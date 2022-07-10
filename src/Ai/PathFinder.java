package Ai;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] node ;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNode();
    }
    public void instantiateNode(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gp.maxWorldCol) {
                row++;
                col = 0;
            }
        }
    }
    public void resetNodes(){
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol&& row < gp.maxWorldRow){
            //reset open, check and solid
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if(col == gp.maxWorldCol){
                col=0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNodes();
        //set start, goal
        startNode = node[startCol][startRow];
        goalNode = node[goalCol][goalRow];
        currentNode = startNode;
        openList.add(currentNode);
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol&& row < gp.maxWorldRow){
            //check solid
            int tileNum = gp.tileM.layer0[col][row];
            if(gp.tileM.tile[tileNum].collision){
                node[col][row].solid = true;
            }
            //check interactiveTile
            for(int i = 0; i < gp.iTile.length;i++){
                if(gp.iTile[i]!=null &&gp.iTile[i].destructible){
                    int itCol = gp.iTile[i].worldX/gp.tileSize;
                    int itRow = gp.iTile[i].worldY/gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol){
                col=0;
                row++;
            }
        }
    }
    public void getCost(Node node){
        int xDist = Math.abs(node.col - startNode.col);
        int yDist = Math.abs(node.row - startNode.row);
        node.gCost = xDist+yDist;
        xDist = Math.abs(node.col - goalNode.col);
        yDist = Math.abs(node.row - goalNode.row);
        node.hCost = xDist+yDist;
        node.fCost = node.gCost+node.hCost;
    }
    public boolean search(){
        while(!goalReached&&step <500){
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            for(int i = 0; i <openList.size();i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if(openList.get(i).fCost == bestNodefCost){
                    if (openList.get(i).fCost < openList.get(bestNodeIndex).gCost)  {
                        bestNodeIndex = i;
                    }
                }
            }
            if(openList.size() == 0) {
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if(currentNode  == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return !goalReached;
    }
    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(current);
            current = current.parent;
        }
    }
    public void openNode(Node node){
        if(!node.open&&!node.checked&&!node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
}
