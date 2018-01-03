//TicTacToe for Player against Computer
package tictactoe;

import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
/**
 *
 * Player   => Symbol: O  Player Number: 1
 * Computer => Symbol: X Player Number: -1
 *
 */
public class TicTacToe {

    static int[] a=new int[9];
    static JFrame f=new JFrame("Tic-Tac-Toe");
    static JButton[] b=new JButton[9];
    static Icon x=new ImageIcon("x.png");
    static Icon o=new ImageIcon("o.png");
    static int pl=1;
    public static void main(String[] args) {

       f.setLayout(new GridLayout(3,3));
       f.setLocation(600,200);
       f.setSize(600,600);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       for(int i=0;i<9;i++){
           b[i]=new JButton();
           f.add(b[i]);
           a[i]=0;
       }

       Handler h=new Handler();
       for(int i=0;i<9;i++)b[i].addActionListener(h);
       f.setVisible(true);

    }

    static void toggle()
    {
        pl*=-1;
    }

    static String possibleMoves()
    {
        String list="";
        for(int i=0;i<9;i++)if(a[i]==0)list+=i;
        return list;
    }
    static String getBestMove()
    {
        String moves=possibleMoves(),bestMove="";
        int rate=-999999999;
        for(int i=0;i<moves.length();i++)
        {
            if(rate<getRating(moves.substring(i,i+1))){
                bestMove=(moves.substring(i,i+1));
                rate=getRating(moves.substring(i,i+1));
            }
        }
        return bestMove;
    }


    static int getRating(String move)
    {   int rating=0;
        makeMove(move);
        if(checkWin()) rating+=pl*1000;
        else
        {
            String moves=possibleMoves();
            for(int i=0;i<moves.length();i++)
            {
               makeMove(moves.substring(i,i+1));
                if(checkWin())  rating-=1000;

                undoMove(moves.substring(i,i+1));
            }
        }
        undoMove(move);
        return rating;
    }

    static void makeMove(String move)
    {

        a[Integer.parseInt(move)]=pl;
        toggle();

    }
    static void undoMove(String move)
    {
        a[Integer.parseInt(move)]=0;
        toggle();
    }
    static boolean checkWin()
    { boolean flag=false;
        for(int i=0;i<9;i++)
        {
            if(i%3==0){
                if(a[i]==a[i+1] && a[i]==a[i+2] && a[i]!=0)flag=true;
            }
            if(i<3){
                if(a[i]==a[i+3] && a[i]==a[i+6] && a[i]!=0)flag=true;
            }

        }
        if(a[0]==a[4] && a[0]==a[8] && a[0]!=0)flag=true;
        if(a[2]==a[4] && a[2]==a[6] && a[2]!=0)flag=true;
        return flag;
    }

    static boolean checkDraw()
    {
        boolean flag=true;
        for(int i=0;i<9;i++)if(a[i]==0)flag=false;
        return flag;
    }

    static void checkBoard()
    {
         if(checkWin()){
                    String s;
                    if(pl==-1)s="You Win";
                    else s="You Loose!!";
                    int ch= JOptionPane.showConfirmDialog(f, s+"\nPlay Again?", s,JOptionPane.YES_NO_OPTION );
                    if(ch==JOptionPane.YES_OPTION)
                    {   for(int i=0;i<9;i++){
                            a[i]=0; b[i].setIcon(null);
                        }
                    }
                    else System.exit(0);
                }
             if(checkDraw()){
                    int ch= JOptionPane.showConfirmDialog(f, "Draw!!\nPlay Again?", "Match Drawn",JOptionPane.YES_NO_OPTION );
                    if(ch==JOptionPane.YES_OPTION)
                    {   for(int i=0;i<9;i++){
                            a[i]=0; b[i].setIcon(null);
                        }
                    }
                    else System.exit(0);
                }
    }

    static class Handler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
           for(int i=0;i<9;i++)
            if(b[i]==ae.getSource()){
                if(a[i]==0)
                {
                 if(pl==1){b[i].setIcon(x); toggle(); a[i]=1;}
                    else {b[i].setIcon(o); toggle(); a[i]=-1;}

                 checkBoard();
                String move=getBestMove();
                makeMove(move); b[Integer.parseInt(move)].setIcon(o);
                checkBoard();
                }

               }


        }
    }

}
