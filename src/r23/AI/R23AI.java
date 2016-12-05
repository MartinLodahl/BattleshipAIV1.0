/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r23.AI;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Random;

/**
 *
 * @author MartinLodahl
 */
public class R23AI implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;

    private int nextX;
    private int nextY;

    boolean[][] foundPosition;

    public R23AI() {
        
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        nextX = 0;
        nextY = 0;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        foundPosition = new boolean[sizeX][sizeY];
        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos;
            if (vertical) {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (s.size() - 1));

                while (true) {
                    int counter = 0;
                    if (counter > 2) {
                        y = rnd.nextInt(sizeY - (s.size() - 1));
                    }
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x][y + j]) {
                            check = true;
                            x++;
                            if (x >= sizeX) {
                                x = 0;
                            }
                        }
                    }
                    if (check == false) {
                        break;
                    }
                    counter++;
                }
                for (int j = 0; j < s.size(); j++) {
                    foundPosition[x][y + j] = true;
                }
                pos = new Position(x, y);
            } else {
                int x = rnd.nextInt(sizeX - (s.size() - 1));
                int y = rnd.nextInt(sizeY);
                while (true) {
                    int counter = 0;
                    if (counter > 2) {
                        x = rnd.nextInt(sizeX - (s.size() - 1));
                    }
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x + j][y]) {
                            check = true;
                            y++;
                            if (y >= sizeX) {
                                y = 0;
                            }
                        }
                    }
                    if (check == false) {
                        break;
                    }
                    counter++;
                }
                for (int j = 0; j < s.size(); j++) {
                    foundPosition[x + j][y] = true;
                }
                pos = new Position(x, y);
            }
            board.placeShip(pos, s, vertical);
        }
    }

    @Override
    public void incoming(Position pos) {

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = new Position(nextX, nextY);
        ++nextX;
        if (nextX >= sizeX) {
            nextX = 0;
            ++nextY;
            if (nextY >= sizeY) {
                nextY = 0;
            }
        }
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        //Do nothing
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        //Do nothing...
    }

    @Override
    public void startRound(int round) {
        //Do nothing
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }
}