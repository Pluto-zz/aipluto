/*
 * Created on 07.09.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author bert
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Coordinate implements Serializable {

    int x, y;

    /**
     * 
     */
    public enum Direction {

        RANGED,
        EAST,
        NORTHEAST,
        NORTHNORTHEAST,
        EASTNORTHEAST,
        NORTHWEST,
        NORTHNORTHWEST,
        WESTNORTHWEST,
        WEST,
        WESTSOUTHWEST,
        SOUTHSOUTHWEST,
        SOUTHWEST,
        SOUTHEAST,
        SOUTHSOUTHEAST,
        EASTSOUTHEAST,
        NORTH,
        SOUTH,
    }

    public Coordinate() {
    }

    public int hashCode() {
        return x * 1000 + y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }

    /**
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * @param i
     */
    public void setX(int i) {
        x = i;
    }

    /**
     * @param i
     */
    public void setY(int i) {
        y = i;
    }

    public Direction getRangedDirection(Coordinate c) {

        int cx = c.getX();
        int cy = c.getY();
        int dx = cx - x;
        int dy = cy - y;

        System.out.println("start: " + this + ", to: " + c + ", diff: " + dx + ", " + dy + ", div: " + (float) dy / dx + ", test: " + (float) 2 / 3);

        if (dy == 0 && dx > 0) {
            return Direction.EAST;
        }
        if (dy == 0 && dx < 0) {
            return Direction.WEST;
        }
        if (dy > 0 && dx == 0) {
            return Direction.SOUTH;
        }
        if (dy < 0 && dx == 0) {
            return Direction.NORTH;
        }
        if (y % 2 == 0) {
            if (dy < 0 && dx > 0) {// && (Math.abs(dy / dx) == 2 || ) ) {{

                if (cx % 2 == 1) {
                    cx += 1;
                    if (cy % 2 == 0) {
                        cy -= 2;
                    }
                }
                if (cy % 2 == 1) {
                    cy -= 1;
                }
                System.out.println("cx: " + cx + ", cy: " + cy);
                if (Math.abs(dy / dx) == 2 || Math.abs((cy - y) / (cx - x)) == 2) {
                    return Direction.NORTHNORTHEAST;
                }
            }
            if (dy < 0 && dx > 0 && Math.abs((float) dy / dx) == (float) 2 / 3) {
                return Direction.EASTNORTHEAST;
            }
            if (dy < 0 && dx < 0) {
                if (cx % 2 == 1) {
                    cx -= 1;
                    if (cy % 2 == 0) {
                        cy -= 2;
                    }
                }
                if (cy % 2 == 1) {
                    cy -= 1;
                }
                System.out.println("cx: " + cx + ", cy: " + cy);
                if (Math.abs(dy / dx) == 2 || Math.abs((cy - y) / (cx - x)) == 2) {
                    return Direction.NORTHNORTHWEST;
                }
            }
            if (dy < 0 && dx < 0 && Math.abs((float) dy / dx) == (float) 2 / 3) {
                return Direction.WESTNORTHWEST;
            }

            if (dy > 0 && dx > 0 && Math.abs(dy / dx) == 2) {
                return Direction.SOUTHSOUTHEAST;
            }
            if (dy > 0 && dx > 0 && Math.abs((float) dy / dx) == (float) 2 / 3) {
                return Direction.EASTSOUTHEAST;
            }
            if (dy > 0 && dx < 0 && Math.abs(dy / dx) == 2) {
                return Direction.SOUTHSOUTHWEST;
            }
            if (dy > 0 && dx < 0 && Math.abs((float) dy / dx) == (float) 2 / 3) {
                return Direction.WESTSOUTHWEST;
            }
        } else {
            if (dy == -1 && dx == 0) {
                return Direction.NORTHWEST;
            }
            if (dy == -1 && dx == 1) {
                return Direction.NORTHEAST;
            }
            if (dy == 1 && dx == 0) {
                return Direction.SOUTHWEST;
            }
            if (dy == 1 && dx == 1) {
                return Direction.SOUTHEAST;
            }
        }
        return Direction.RANGED;

    }

    public Direction getDirection(Coordinate c) {
        int dx = c.getX() - x;
        int dy = c.getY() - y;
        if (dx < -1 || dx > 1 || dy < -1 || dy > 1) {
            return Direction.RANGED;
        }
        if (dy == 0 && dx == 1) {
            return Direction.EAST;
        }
        if (dy == 0 && dx == -1) {
            return Direction.WEST;
        }
        if (y % 2 == 0) {
            if (dy == -1 && dx == -1) {
                return Direction.NORTHWEST;
            }
            if (dy == -1 && dx == 0) {
                return Direction.NORTHEAST;
            }
            if (dy == 1 && dx == -1) {
                return Direction.SOUTHWEST;
            }
            if (dy == 1 && dx == 0) {
                return Direction.SOUTHEAST;
            }
        } else {
            if (dy == -1 && dx == 0) {
                return Direction.NORTHWEST;
            }
            if (dy == -1 && dx == 1) {
                return Direction.NORTHEAST;
            }
            if (dy == 1 && dx == 0) {
                return Direction.SOUTHWEST;
            }
            if (dy == 1 && dx == 1) {
                return Direction.SOUTHEAST;
            }
        }
        return Direction.RANGED;
    }

    public static Direction oppositeDirection(Direction dir) {
        if (dir == Direction.WEST) {
            return Direction.EAST;
        } else if (dir == Direction.NORTHWEST) {
            return Direction.SOUTHEAST;
        } else if (dir == Direction.NORTHEAST) {
            return Direction.SOUTHWEST;
        } else if (dir == Direction.EAST) {
            return Direction.WEST;
        } else if (dir == Direction.SOUTHEAST) {
            return Direction.NORTHWEST;
        } else if (dir == Direction.SOUTHWEST) {
            return Direction.NORTHEAST;
        } else {
            return Direction.RANGED;
        }
    }

    public int getDistance(Coordinate c) {

        int yh = y % 2;
        int x1 = new Double(x - Math.ceil((Math.abs(c.y - y) - yh) / 2.0)).intValue();
        int x2 = new Double(x + Math.floor((Math.abs(c.y - y) + yh) / 2.0)).intValue();
        if (x1 <= c.x && x2 >= c.x) {
            return Math.abs(c.y - y);
        } else if (x1 > c.x) {
            return Math.abs(c.y - y) + Math.abs(c.x - x1);
        } else {
            return Math.abs(c.y - y) + Math.abs(c.x - x2);
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Coordinate)) {
            return false;
        } else {
            Coordinate c = (Coordinate) arg0;
            return c.x == x && c.y == y;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    /**
     * @param i
     * @return
     */
    public Coordinate getCoordinateInDirection(Direction dir) {
        int x = this.x;
        int y = this.y;
        if (y % 2 == 0) {
            if (dir == Direction.EAST) {
                x += +1;
                y += 0;
            } else if (dir == Direction.WEST) {
                x += -1;
                y += 0;
            } else if (dir == Direction.NORTHEAST) {
                x += 0;
                y += -1;
            } else if (dir == Direction.NORTHWEST) {
                x += -1;
                y += -1;
            } else if (dir == Direction.SOUTHEAST) {
                x += 0;
                y += 1;
            } else if (dir == Direction.SOUTHWEST) {
                x += -1;
                y += 1;
            }
        } else {
            if (dir == Direction.EAST) {
                x += +1;
                y += 0;
            } else if (dir == Direction.WEST) {
                x += -1;
                y += 0;
            } else if (dir == Direction.NORTHEAST) {
                x += 1;
                y += -1;
            } else if (dir == Direction.NORTHWEST) {
                x += 0;
                y += -1;
            } else if (dir == Direction.SOUTHEAST) {
                x += 1;
                y += 1;
            } else if (dir == Direction.SOUTHWEST) {
                x += 0;
                y += 1;
            }

        }
        return new Coordinate(x, y);
    }

    public static Direction clockwise(Direction dir) {
        if (dir == Direction.EAST) {
            return Direction.SOUTHEAST;
        } else if (dir == Direction.WEST) {
            return Direction.NORTHWEST;
        } else if (dir == Direction.NORTHEAST) {
            return Direction.EAST;
        } else if (dir == Direction.NORTHWEST) {
            return Direction.NORTHEAST;
        } else if (dir == Direction.SOUTHEAST) {
            return Direction.SOUTHWEST;
        }
        return Direction.WEST;
    }
    static final Collection<Direction> dirs = new LinkedList<Direction>();
    

    static {
        dirs.add(Direction.EAST);
        dirs.add(Direction.NORTHEAST);
        dirs.add(Direction.NORTHWEST);
        dirs.add(Direction.SOUTHEAST);
        dirs.add(Direction.SOUTHWEST);
        dirs.add(Direction.WEST);
    }

    public static Collection<Direction> getAllDirections() {
        return dirs;
    }
    int n;

    public List<Coordinate> getCircle(int radius) {

        List<Coordinate> l = new LinkedList<Coordinate>();
        if (radius == 0) {
            // this coord gets added in the algorithm below... only need if radius = 0
            l.add(this);
            return l;
        }
        for (int ix = getX() - radius; ix < getX() + radius + 1; ix++) {
            for (int iy = getY() - radius; iy < getY() + radius + 1; iy++) {
                //     System.out.print(" " + ix + "," + iy);
                Coordinate to = new Coordinate(ix, iy);
                if (getDistance(to) <= radius) {
                    l.add(to);
                }
            }
        //  System.out.println();
        }
        return l;
    }

    public List<Coordinate> getSquare(int radius) {

        List<Coordinate> l = new LinkedList<Coordinate>();
        if (radius == 0) {
            return l;
        }
        for (int ix = getX() - radius; ix < getX() + radius + 1; ix++) {
            for (int iy = getY() - radius; iy < getY() + radius + 1; iy++) {
                Coordinate to = new Coordinate(ix, iy);
                l.add(to);
            }
        }
        return l;
    }
}
