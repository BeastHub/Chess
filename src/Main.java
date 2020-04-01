import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        new ChessGame();
    }
}

class ChessGame extends JFrame {
    public static final int WIDTH=800, HEIGHT=800;
    ArrayList<FieldPanel> listOfFields = new ArrayList<>();
    public ChessGame(){
        setResizable(false);
        setTitle("Chess by Beast :)");
        setLayout(new GridLayout(8,8));
        setSize(WIDTH,HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

        drawBoard();
        loadIcons();
    }

    public void drawBoard(){
        Color whiteColor = new Color(255, 211, 174);
        Color blackColor = new Color(133, 99, 72);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                FieldPanel field = new FieldPanel();
                field.setVisible(true);
                listOfFields.add(field);
                if(i%2==0){
                    if (j%2==0){
                        field.setBackground(whiteColor);
                    }else{
                        field.setBackground(blackColor);
                    }
                }else{
                    if (j%2==0){
                        field.setBackground(blackColor);
                    }else{
                        field.setBackground(whiteColor);
                    }
                }
                add(field);
            }
        }
    }

    public void loadIcons(){
        BufferedImage bigImg = null;
        try {
            bigImg = ImageIO.read(new File("res/icons.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int width = bigImg.getWidth()/6;
        final int height = bigImg.getHeight()/2;
        final int rows = 2;
        final int cols = 6;
        BufferedImage[] sprites = new BufferedImage[rows * cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                sprites[(i * cols) + j] = bigImg.getSubimage(j * width,i * height, width, height);
        }
        }

        for(int i = 0; i < sprites.length ; i++){
            sprites[i] = resize(sprites[i],WIDTH/8, HEIGHT/8);
        }

        int counterOfFields = 0;
        for(FieldPanel field : listOfFields){
            field.setLayout(new GridBagLayout());
            if(counterOfFields == 0 || counterOfFields == 7){ // black rooks
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[10]));
                icon.setVisible(true);
                field.setFigure(icon, "BROOK");
                field.add(icon);
            }
            if(counterOfFields == 1 || counterOfFields == 6){ // black knights
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[9]));
                icon.setVisible(true);
                field.setFigure(icon, "BKNIGHT");
                field.add(icon);
            }
            if(counterOfFields == 2 || counterOfFields == 5){ // black bishops
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[8]));
                icon.setVisible(true);
                field.setFigure(icon, "BBISHOP");
                field.add(icon);
            }
            if(counterOfFields == 3){                         // black queen
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[7]));
                icon.setVisible(true);
                field.setFigure(icon, "BQUEEN");
                field.add(icon);
            }
            if(counterOfFields == 4){                         // black king
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[6]));
                icon.setVisible(true);
                field.setFigure(icon, "BKING");
                field.add(icon);
            }
            for(int i = 8; i < 16; i++){
                if(counterOfFields == i){                     // black pawns
                    JLabel icon = new JLabel();
                    icon.setIcon(new ImageIcon(sprites[11]));
                    icon.setVisible(true);
                    field.setFigure(icon, "BPAWN");
                    field.add(icon);
                }
            }
            for(int i = 48; i < 56; i++){
                if(counterOfFields == i){                     // white pawns
                    JLabel icon = new JLabel();
                    icon.setIcon(new ImageIcon(sprites[5]));
                    icon.setVisible(true);
                    field.setFigure(icon, "WPAWN");
                    field.add(icon);
                }
            }
            if(counterOfFields == 56 || counterOfFields == 63){ // white rooks
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[4]));
                icon.setVisible(true);
                field.setFigure(icon, "WROOK");
                field.add(icon);
            }
            if(counterOfFields == 57 || counterOfFields == 62){ // white knights
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[3]));
                icon.setVisible(true);
                field.setFigure(icon, "WKNIGHT");
                field.add(icon);
            }
            if(counterOfFields == 58 || counterOfFields == 61){ // white bishops
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[2]));
                icon.setVisible(true);
                field.setFigure(icon, "WBISHOP");
                field.add(icon);
            }
            if(counterOfFields == 59){ // white queen
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[1]));
                icon.setVisible(true);
                field.setFigure(icon, "WQUEEN");
                field.add(icon);
            }
            if(counterOfFields == 60){ // white king
                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon(sprites[0]));
                icon.setVisible(true);
                field.setFigure(icon, "WKING");
                field.add(icon);
            }
            counterOfFields++;
        }
        pack();
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

class FieldPanel extends JPanel{
    JPanel field;
    JLabel figure;
    FigureType figureType;
    public FieldPanel(){
        field = new JPanel();
        figure = new JLabel();
    }
    public enum FigureType{
        WPAWN, WKNIGHT, WBISHOP, WROOK, WQUEEN, WKING, BPAWN, BKNIGHT, BBISHOP, BROOK, BQUEEN, BKING;
    }


    public JLabel getFigure() {
        return figure;
    }

    public void setFigure(JLabel figure, String figureType) {
        this.figure = figure;
        if(figureType.equals("WPAWN")) this.figureType = FigureType.WPAWN;
        if(figureType.equals("WKNIGHT")) this.figureType = FigureType.WKNIGHT;
        if(figureType.equals("WBISHOP")) this.figureType = FigureType.WBISHOP;
        if(figureType.equals("WROOK")) this.figureType = FigureType.WROOK;
        if(figureType.equals("WQUEEN")) this.figureType = FigureType.WQUEEN;
        if(figureType.equals("WKING")) this.figureType = FigureType.WKING;

        if(figureType.equals("BPAWN")) this.figureType = FigureType.BPAWN;
        if(figureType.equals("BKNIGHT")) this.figureType = FigureType.BKNIGHT;
        if(figureType.equals("BBISHOP")) this.figureType = FigureType.BBISHOP;
        if(figureType.equals("BROOK")) this.figureType = FigureType.BROOK;
        if(figureType.equals("BQUEEN")) this.figureType = FigureType.BQUEEN;
        if(figureType.equals("BKING")) this.figureType = FigureType.BKING;
    }

}