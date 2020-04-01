import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    ArrayList<ImageIcon> listOfIcons = new ArrayList<>();
    Boolean selectedFigure = false;
    FieldPanel oldField;
    Boolean whiteToMove=true;
    BufferedImage[] sprites;
    ImageIcon wPawnIcon, wKnightIcon, wBishopIcon, wRookIcon, wQueenIcon, wKingIcon, bPawnIcon, bKnightIcon, bBishopIcon, bRookIcon, bQueenIcon, bKingIcon;
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
        addListeners(listOfFields);
    }

    public void drawBoard(){
        Color whiteColor = new Color(255, 211, 174);
        Color blackColor = new Color(133, 99, 72);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                FieldPanel field = new FieldPanel(j,i);
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
        sprites = new BufferedImage[rows * cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                sprites[(i * cols) + j] = bigImg.getSubimage(j * width,i * height, width, height);
        }
        }

        for(int i = 0; i < sprites.length ; i++){
            sprites[i] = resize(sprites[i],WIDTH/8, HEIGHT/8);
            listOfIcons.add(new ImageIcon(sprites[i]));
        }
        wPawnIcon=listOfIcons.get(5);
        wKnightIcon=listOfIcons.get(3);
        wBishopIcon=listOfIcons.get(2);
        wRookIcon=listOfIcons.get(4);
        wQueenIcon=listOfIcons.get(1);
        wKingIcon=listOfIcons.get(0);
        bPawnIcon=listOfIcons.get(11);
        bKnightIcon=listOfIcons.get(9);
        bBishopIcon=listOfIcons.get(8);
        bRookIcon=listOfIcons.get(10);
        bQueenIcon=listOfIcons.get(7);
        bKingIcon=listOfIcons.get(6);

        int counterOfFields = 0;
        for(FieldPanel field : listOfFields){
            field.setLayout(new GridBagLayout());
            if(counterOfFields == 18){
                JLabel icon = new JLabel();
                icon.setIcon(wPawnIcon);
                field.setFigure(icon, FieldPanel.FigureType.WPAWN);
            }
            if(counterOfFields == 0 || counterOfFields == 7){ // black rooks
                JLabel icon = new JLabel();
                icon.setIcon(bRookIcon);
                field.setFigure(icon, FieldPanel.FigureType.BROOK);
            }
            if(counterOfFields == 1 || counterOfFields == 6){ // black knights
                JLabel icon = new JLabel();
                icon.setIcon(bKnightIcon);
                field.setFigure(icon, FieldPanel.FigureType.BKNIGHT);
            }
            if(counterOfFields == 2 || counterOfFields == 5){ // black bishops
                JLabel icon = new JLabel();
                icon.setIcon(bBishopIcon);
                field.setFigure(icon, FieldPanel.FigureType.BBISHOP);
            }
            if(counterOfFields == 3){                         // black queen
                JLabel icon = new JLabel();
                icon.setIcon(bQueenIcon);
                field.setFigure(icon, FieldPanel.FigureType.BQUEEN);
            }
            if(counterOfFields == 4){                         // black king
                JLabel icon = new JLabel();
                icon.setIcon(bKingIcon);
                field.setFigure(icon, FieldPanel.FigureType.BKING);
            }
            for(int i = 8; i < 16; i++){
                if(counterOfFields == i){                     // black pawns
                    JLabel icon = new JLabel();
                    icon.setIcon(bPawnIcon);
                    field.setFigure(icon, FieldPanel.FigureType.BPAWN);
                }
            }
            for(int i = 48; i < 56; i++){
                if(counterOfFields == i){                     // white pawns
                    JLabel icon = new JLabel();
                    icon.setIcon(wPawnIcon);
                    field.setFigure(icon, FieldPanel.FigureType.WPAWN);
                }
            }
            if(counterOfFields == 56 || counterOfFields == 63){ // white rooks
                JLabel icon = new JLabel();
                icon.setIcon(wRookIcon);
                field.setFigure(icon, FieldPanel.FigureType.WROOK);
            }
            if(counterOfFields == 57 || counterOfFields == 62){ // white knights
                JLabel icon = new JLabel();
                icon.setIcon(wKnightIcon);
                field.setFigure(icon, FieldPanel.FigureType.WKNIGHT);
            }
            if(counterOfFields == 58 || counterOfFields == 61){ // white bishops
                JLabel icon = new JLabel();
                icon.setIcon(wBishopIcon);
                field.setFigure(icon, FieldPanel.FigureType.WBISHOP);
            }
            if(counterOfFields == 59){ // white queen
                JLabel icon = new JLabel();
                icon.setIcon(wQueenIcon);
                field.setFigure(icon, FieldPanel.FigureType.WQUEEN);
            }
            if(counterOfFields == 60){ // white king
                JLabel icon = new JLabel();
                icon.setIcon(wKingIcon);
                field.setFigure(icon, FieldPanel.FigureType.WKING);
            }
            counterOfFields++;
        }
        pack();
    }

    public void addListeners(ArrayList<FieldPanel> listOfFields) {
        for(FieldPanel field : listOfFields){
            field.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);

                    checkPossibleMoves(field); // add fields to list possibleMoves
                    if(SwingUtilities.isLeftMouseButton(e)){
                        if(whiteToMove){
                            if(field.getWhitePresent() && !selectedFigure){
                                oldField = field;
                                selectedFigure = true;
                                showPossibleMoves(field);
                                field.setSelected();
                            }else if(selectedFigure){
                                if(!field.getWhitePresent()){
                                    selectedFigure = false;
                                    moveFigure(oldField, field);
                                }else if(field.getWhitePresent()){
                                    oldField.setUnSelected();
                                    oldField = field;
                                    showPossibleMoves(field);
                                    field.setSelected();
                                }
                            }
                        }
                    }
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                }
            });
        }
    }

    private void moveFigure(FieldPanel oldField, FieldPanel field) {
        if(oldField.getPossibleMoves().contains(field)){
            field.setFigure(oldField.getFigure(),oldField.getFigureType());
            field.getFigure().repaint();
            field.repaint();
            oldField.removeFigure();
            oldField.repaint();
        }
        for(FieldPanel clearField : listOfFields){
            clearField.setUnSelected();
        }
    }

    private void checkPossibleMoves(FieldPanel field) {
        FieldPanel possibleField, possibleTakeField;
        if(!field.isEmpty() || field.getWhitePresent() || field.getBlackPresent()){
            if(field.getFigureType().equals(FieldPanel.FigureType.WPAWN)){
                if(field.getFigureY()==6){

                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-1); // 1 up
                    if(possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-2); // 2 up
                    if(possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                }else if(field.getFigureY() < 6  && field.getFigureY()>=0){
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-1); // 1 up
                    if(possibleField.isEmpty()){ field.addPossibleMoves(possibleField); }
                }

                if(field.getFigureX()-1 >=0 && field.getFigureY()-1 >=0 && field.getFigureX()+1 <=7){ // not first row and not first column and not last column
                    possibleTakeField = getFieldByXY(field.getFigureX()-1, field.figureY-1);
                    if(!possibleTakeField.isEmpty() && possibleTakeField.getBlackPresent()){
                        field.addPossibleMoves(possibleTakeField);
                    }

                    possibleTakeField = getFieldByXY(field.getFigureX()+1, field.figureY-1);
                    if(!possibleTakeField.isEmpty() && possibleTakeField.getBlackPresent()){
                        field.addPossibleMoves(possibleTakeField);
                    }
                }
            }
        }
    }
    private void showPossibleMoves(FieldPanel field){
        for(FieldPanel clearField : listOfFields){
            clearField.setUnSelected();
        }
        for(FieldPanel foundField : field.getPossibleMoves()){
            foundField.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        }
    }

    private FieldPanel getFieldByXY(Integer figureX, Integer figureY) {
        for(FieldPanel field : listOfFields){
            if(field.getFigureX() == figureX && field.getFigureY() == figureY){
                return field;
            }
        }
        return null;
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
    Integer figureX,figureY;
    Boolean whitePresent, blackPresent;
    ArrayList<FieldPanel> possibleMoves;
    public FieldPanel(Integer figureX, Integer figureY){
        this.possibleMoves = new ArrayList<FieldPanel>();
        this.whitePresent = false;
        this.blackPresent = false;
        this.figureX = figureX;
        this.figureY = figureY;
        field = new JPanel();
        figure = new JLabel();
    }

    public void setSelected() {
        setBorder(BorderFactory.createLineBorder(new Color(193, 255, 179), 5));
    }
    public void setUnSelected() {
        setBorder(null);
    }

    public boolean isEmpty() {
        if (this.figure.getIcon() == null) return true;
        else return false;
    }

    public void addPossibleMoves(FieldPanel possibleField) {
        getPossibleMoves().add(possibleField);
    }

    public enum FigureType{
        WPAWN, WKNIGHT, WBISHOP, WROOK, WQUEEN, WKING, BPAWN, BKNIGHT, BBISHOP, BROOK, BQUEEN, BKING;
    }

    public ArrayList<FieldPanel> getPossibleMoves(){
        return this.possibleMoves;
    }

    public Integer getFigureX() {
        return figureX;
    }

    public Integer getFigureY() {
        return figureY;
    }

    public JLabel getFigure() {
        return figure;
    }

    public Boolean getWhitePresent() {
        return whitePresent;
    }

    public void setWhitePresent(Boolean setWhitePresent) {
        this.whitePresent = setWhitePresent;
    }

    public Boolean getBlackPresent() {
        return blackPresent;
    }

    public void setBlackPresent(Boolean setBlackPresent) {
        this.blackPresent = setBlackPresent;
    }

    public void setFigure(JLabel figure, FigureType figureType) {
        this.figure = figure;
        this.figureType = figureType;
        this.figure.setIcon(figure.getIcon());
        if(figureType.toString().startsWith("W")) this.setWhitePresent(true);
        else this.setWhitePresent(false);
        if(figureType.toString().startsWith("B")) this.setBlackPresent(true);
        else this.setBlackPresent(false);
        add(figure);
        repaint();
    }

    public FigureType getFigureType() {
        return this.figureType;
    }

    public void removeFigure(){
        getFigure().setIcon(null);
        repaint();
        setWhitePresent(null);
        setBlackPresent(null);
    }

    public void displayPossibleMoves(){
        System.out.println(getFigureX() + ", " + getFigureY());
    }
}