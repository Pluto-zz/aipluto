
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Eliza {

    String username;
    String token;
    String urlString;

    public Eliza(String ur, String u, String t) {
        username = u;
        urlString = ur;
        token = t;
    }

    public String httpRequest(String u, String username, String password, String xml) throws IOException {
        URL url = new URL(u);
        //logger.debug("Requesting: "+urlString );
        URLConnection conn = url.openConnection();
        if (username != null) {
            String userPassword = username + ":" + password;
            String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encoding);
        }
        conn.setConnectTimeout(2000);
        if (xml != null) {
            conn.addRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);
            conn.getOutputStream().write(xml.getBytes());
        }
        conn.connect();
        String status = conn.getHeaderField("Status");
        if (!(status != null && status.contains("404"))) {
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringWriter str = new StringWriter();
            PrintWriter out = new PrintWriter(str);
            String line;
            line = rd.readLine();
            while (line != null) {
                out.println(line);
                line = rd.readLine();
            }
            //logger.debug( "str: "+str );

            return str.toString();
        } else {
            return null;
        }
    }

    public boolean acceptInvite(int id) throws IOException {
        String result = httpRequest(urlString + "eliza", username, token, "<weewar game=\"" + id + "\"><acceptInvitation/></weewar>");
        return result.contains("<ok/>");
    }

    public Collection<Game> headquarterGames() throws IOException, JDOMException {
        String xml = httpRequest(urlString + "headquarters", username, token, null);
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        doc = builder.build(new StringReader(xml));
        Collection<Game> ret = new LinkedList<Game>();
        //System.out.println(" Doc: "+doc.getRootElement().getChildren("game") );
        Collection<Element> games = doc.getRootElement().getChildren("game");
        for (Element gameEle : games) {
            if (!gameEle.getChildText("state").equals("finished")) {
                Game g = parseGame(gameEle);
                ret.add(g);
            }
        }
        return ret;
    }

    // i split up the following functions so a Faction can refresh it's units by
    // calling the gamestate api with out having to re-make a whole new Game object
    public Document getGameStateXML(int id) throws IOException, JDOMException {
        String xml = httpRequest(urlString + "gameState.jsp?game=" + id, username, token, "<weewar game=\"" + id + "\"><acceptInvitation/></weewar>");
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        doc = builder.build(new StringReader(xml));
        return doc;
    }

    public Game getParsedGameState(int id) throws IOException, JDOMException {
        Document doc = getGameStateXML(id);
        return parseGame(doc.getRootElement());
    }

    public void refreshFaction(int id, Faction faction) throws IOException, JDOMException {
        Document doc = getGameStateXML(id);
        Element gameEle = doc.getRootElement();
        if (gameEle.getChild("factions") != null) {
            List<Element> factions = gameEle.getChild("factions").getChildren("faction");
            for (Element factionEle : factions) {
                if (faction.getPlayerName().equals(factionEle.getAttributeValue("playerName"))) {
                    parseFactionElements(faction, factionEle);
                }
            }
        }
    }

    private void parseFactionElements(Faction f, Element faction) {
        f.setPlayerName(faction.getAttributeValue("playerName"));
        f.setState(faction.getAttributeValue("state"));
        if (faction.getAttributeValue("credits") != null) {
            f.setCredits(Integer.parseInt(faction.getAttributeValue("credits")));
        }
        for (Element unit : (List<Element>) faction.getChildren("unit")) {
            Unit u = new Unit();
            u.setCoordinate(new Coordinate(Integer.parseInt(unit.getAttributeValue("x")), Integer.parseInt(unit.getAttributeValue("y"))));
            u.setType(unit.getAttributeValue("type"));
            u.setFinished("true".equals(unit.getAttributeValue("finished")));
            u.setQuantity(Integer.parseInt(unit.getAttributeValue("quantity")));
            f.getUnits().add(u);

            // add in some stats
            f.unitStats.addUnit(u);
        }
        for (Element unit : (List<Element>) faction.getChildren("terrain")) {
            Terrain t = new Terrain();
            t.setCoordinate(new Coordinate(Integer.parseInt(unit.getAttributeValue("x")), Integer.parseInt(unit.getAttributeValue("y"))));
            t.setType(unit.getAttributeValue("type"));
            t.setFinished("true".equals(unit.getAttributeValue("finished")));
            f.getTerrains().add(t);

            // add in some more stats
            f.unitStats.addBase(t);
        }

    //more stats
    //need to add this function to game.java
    //f.unitStats.setIncome(g.getBaseIncome())
    }

    private Game parseGame(Element gameEle) {
        Game g = new Game();
        String att = gameEle.getAttributeValue("inNeedOfAttention");
        g.setInNeedOfAttention(att != null && att.equals("true"));
        g.setName(gameEle.getChildText("name"));
        g.setId(Integer.parseInt(gameEle.getChildText("id")));
        g.setState(gameEle.getChildText("state"));
        if (gameEle.getChildText("map") != null) {
            g.setMapId(Integer.parseInt(gameEle.getChildText("map")));
        }
        if (gameEle.getChildText("round") != null) {
            g.setRound(Integer.parseInt(gameEle.getChildText("round")));
        }
        g.setLink(gameEle.getChildText("url"));
        g.setRequiringAnInviteAccept(g.getLink().contains("join"));
        if (gameEle.getChild("factions") != null) {
            List<Element> factions = gameEle.getChild("factions").getChildren("faction");
            for (Element faction : factions) {

                Faction f = new Faction();
                g.getFactions().add(f);

                parseFactionElements(f, faction);
            }
        }
        return g;
    }

    private WeewarMap parseMap(Element mapEle) {
        WeewarMap wmap = new WeewarMap();
        wmap.setWidth(Integer.parseInt(mapEle.getChildText("width")));
        wmap.setHeight(Integer.parseInt(mapEle.getChildText("height")));
        if (mapEle.getChild("terrains") != null) {
            Collection<Element> terrians = mapEle.getChild("terrains").getChildren("terrain");
            for (Element terrain : terrians) {
                Terrain t = new Terrain();
                t.setCoordinate(new Coordinate(Integer.parseInt(terrain.getAttributeValue("x")), Integer.parseInt(terrain.getAttributeValue("y"))));
                t.setType(terrain.getAttributeValue("type"));
                wmap.add(t);
            }
        }
        return wmap;
    }

    public List<Coordinate> getMovementCoords(int id, Coordinate from, String type) throws IOException, JDOMException {
        String requestXml = "<weewar game='" + id + "'><movementOptions x='" + from.getX() + "' y='" + from.getY() + "' type='" + type + "' /></weewar>";
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xml));
        List<Coordinate> coords = new LinkedList<Coordinate>();
        for (Element coord : (Collection<Element>) doc.getRootElement().getChildren("coordinate")) {
            Coordinate c = new Coordinate(Integer.parseInt(coord.getAttributeValue("x")), Integer.parseInt(coord.getAttributeValue("y")));
            coords.add(c);
        }
        return coords;
    }

    public String moveAttackCapture(int id, Coordinate from, Coordinate to, Coordinate attack, boolean capture) throws IOException {
        String attackString = "";
        if (attack != null) {
            attackString = "<attack x='" + attack.getX() + "' y='" + attack.getY() + "' />";
        }
        String moveString = "";
        if (to != null && !from.equals(to)) {
            moveString = "<move x='" + to.getX() + "' y='" + to.getY() + "' />";
        }
        String captureString = capture ? "<capture/>" : "";

        String requestXml = "<weewar game='" + id + "'><unit x='" + from.getX() + "' y='" + from.getY() + "' >" + moveString + captureString + attackString + "</unit></weewar>";
        //System.out.println("requestXml: " + requestXml);
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        return xml;
    }

    public String finishTurn(int id) throws IOException {
        String requestXml = "<weewar game='" + id + "'><finishTurn /></weewar>";
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        return xml;
    }

    public String build(int id, Coordinate c, String type) throws IOException {
        String requestXml = "<weewar game='" + id + "'><build  x='" + c.getX() + "' y='" + c.getY() + "' type='" + type + "' /></weewar>";
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        return xml;
    }

    public List<Coordinate> getAttackCoords(int id,
            Coordinate from, String type) throws IOException, JDOMException {
        String requestXml = "<weewar game='" + id + "'><attackOptions x='" + from.getX() + "' y='" + from.getY() + "' type='" + type + "' /></weewar>";
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        System.out.println("attack options request result: " + xml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xml));
        List<Coordinate> coords = new LinkedList<Coordinate>();
        for (Element coord : (Collection<Element>) doc.getRootElement().getChildren("coordinate")) {
            Coordinate c = new Coordinate(Integer.parseInt(coord.getAttributeValue("x")), Integer.parseInt(coord.getAttributeValue("y")));
            coords.add(c);
        }
        return coords;
    }

    public WeewarMap getMap(int mapId) throws JDOMException, IOException {
        String xml = httpRequest(urlString + "mapLayout.jsp?map=" + mapId, username, token, null);
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        doc = builder.build(new StringReader(xml));
        Collection<Game> ret = new LinkedList<Game>();
        //System.out.println(" Doc: "+doc.getRootElement().getChildren("game") );
        return parseMap(doc.getRootElement());
    }
    // --spadequack's function-- this calls the non-AI api to get the map id of a game the AI is invited to
    public int getGameMapId(int id) throws IOException, JDOMException {
        String xml = httpRequest(urlString + "game/" + id, username, token, "");
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        doc = builder.build(new StringReader(xml));
        return Integer.parseInt(doc.getRootElement().getChildText("map"));
    }

    public String repair(int id, Coordinate coordinate) throws IOException {
        String requestXml = "<weewar game='" + id + "'><unit x='" + coordinate.getX() + "' y='" + coordinate.getY() + "' ><repair/></unit></weewar>";
        String xml = httpRequest(urlString + "eliza", username, token, requestXml);
        return xml;

    }
}
