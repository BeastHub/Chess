import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    HashMap<String, String> history = new HashMap<>();
    Color whiteColor= new Color(255, 211, 174);
    Color blackColor= new Color(133, 99, 72);
    Color selectedColor= new Color(161, 139, 132);
    Color oldColor;
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
            // black rooks
            if(counterOfFields == 0 || counterOfFields == 7){
                JLabel icon = new JLabel();
                icon.setIcon(bRookIcon);
                field.setFigure(icon, FieldPanel.FigureType.BROOK);
            }
            // black knights
            if(counterOfFields == 1 || counterOfFields == 6){
                JLabel icon = new JLabel();
                icon.setIcon(bKnightIcon);
                field.setFigure(icon, FieldPanel.FigureType.BKNIGHT);
            }
            // black bishops
            if(counterOfFields == 2 || counterOfFields == 5){
                JLabel icon = new JLabel();
                icon.setIcon(bBishopIcon);
                field.setFigure(icon, FieldPanel.FigureType.BBISHOP);
            }
            // black queen
            if(counterOfFields == 3){
                JLabel icon = new JLabel();
                icon.setIcon(bQueenIcon);
                field.setFigure(icon, FieldPanel.FigureType.BQUEEN);
            }
            // black king
            if(counterOfFields == 4){
                JLabel icon = new JLabel();
                icon.setIcon(bKingIcon);
                field.setFigure(icon, FieldPanel.FigureType.BKING);
            }
            // black pawns
            for(int i = 8; i < 16; i++){
                if(counterOfFields == i){
                    JLabel icon = new JLabel();
                    icon.setIcon(bPawnIcon);
                    field.setFigure(icon, FieldPanel.FigureType.BPAWN);
                }
            }
            // white pawns
            for(int i = 48; i < 56; i++){
                if(counterOfFields == i){
                    JLabel icon = new JLabel();
                    icon.setIcon(wPawnIcon);
                    field.setFigure(icon, FieldPanel.FigureType.WPAWN);
                }
            }
            // white rooks
            if(counterOfFields == 56 || counterOfFields == 63){
                JLabel icon = new JLabel();
                icon.setIcon(wRookIcon);
                field.setFigure(icon, FieldPanel.FigureType.WROOK);
            }
            // white knights
            if(counterOfFields == 57 || counterOfFields == 62){
                JLabel icon = new JLabel();
                icon.setIcon(wKnightIcon);
                field.setFigure(icon, FieldPanel.FigureType.WKNIGHT);
            }
            // white bishops
            if(counterOfFields == 58 || counterOfFields == 61){
                JLabel icon = new JLabel();
                icon.setIcon(wBishopIcon);
                field.setFigure(icon, FieldPanel.FigureType.WBISHOP);
            }
            // white queen
            if(counterOfFields == 59){
                JLabel icon = new JLabel();
                icon.setIcon(wQueenIcon);
                field.setFigure(icon, FieldPanel.FigureType.WQUEEN);
            }
            // white king
            if(counterOfFields == 60){
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
                    if(SwingUtilities.isMiddleMouseButton(e)) {
                        restartGame();
                    }
                    if(SwingUtilities.isRightMouseButton(e)) {
                        printHistory();
                    }
                    checkPossibleMoves(field); // add fields to list possibleMoves
                    if(SwingUtilities.isLeftMouseButton(e)){
                        if(whiteToMove){
                            if(field.getWhitePresent() && !selectedFigure){
                                oldField = field;
                                selectedFigure = true;
                                oldColor = field.getBackground();
                                showPossibleMoves(field);
                                field.setSelected();
                            }else if(selectedFigure){
                                if(!field.getWhitePresent()){
                                    selectedFigure = false;
                                    if(moveFigure(oldField, field)){
                                        field.setBackground(oldColor);
                                        whiteToMove=false;
                                    }
                                }else if(field.getWhitePresent()){
                                    oldField.setUnSelected();
                                    oldField = field;
                                    showPossibleMoves(field);
                                    field.setSelected();
                                }
                            }
                        }else{
                            if(field.getBlackPresent() && !selectedFigure){
                                oldField = field;
                                selectedFigure = true;
                                oldColor = field.getBackground();
                                showPossibleMoves(field);
                                field.setSelected();
                            }else if(selectedFigure){
                                if(!field.getBlackPresent()){
                                    selectedFigure = false;
                                    if(moveFigure(oldField, field)){
                                        field.setBackground(oldColor);
                                        whiteToMove=true;
                                    }
                                }else if(field.getBlackPresent()){
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
                @Override
                public void mouseEntered(MouseEvent e){
                    super.mouseEntered(e);
                    if(selectedFigure){
                        if(field.isPossibleMove()){
                            oldColor = field.getBackground();
                            field.setBackground(selectedColor);
                        }
                    }
                }
                @Override
                public void mouseExited(MouseEvent e){
                    super.mouseExited(e);
                    if(selectedFigure){
                        if(field.isPossibleMove()){
                            field.setBackground(oldColor);
                        }
                    }
                }
            });
        }
    }

    private void restartGame() {
        whiteToMove = true;
        history.clear();
        for(FieldPanel field : listOfFields){
            field.removeFigure();
            field.setUnSelected();
        }
        loadIcons();
    }

    private void printHistory() {
        history.entrySet().forEach(move->{
            System.out.println(move.getKey() + " -> " + move.getValue());
        });
        System.out.println("_________________________________________________________________________________________________________");
    }

    private Boolean moveFigure(FieldPanel oldField, FieldPanel field) {
        Boolean moved = false;
        if(oldField.getPossibleMoves().contains(field)){
            moved = true;
            field.removeFigure();
            field.setFigure(oldField.getFigure(),oldField.getFigureType());
            history.put(oldField.toString(oldField.getFigureType().toString()), field.toString(field.getFigureType().toString()));
            oldField.removeFigure();
            oldField.repaint();
        }
        for(FieldPanel clearField : listOfFields){
            clearField.setUnSelected();
        }
        return moved;
    }

    private void checkPossibleMoves(FieldPanel field) {
        field.getPossibleMoves().removeAll(field.getPossibleMoves());
        FieldPanel possibleField;
        Boolean foundPiece = false;
        if(!field.isEmpty()){
            if(field.getFigureType() == FieldPanel.FigureType.WPAWN){
                foundPiece = false;
                if(field.getFigureY()==6){
                    //2 fields to up
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-1);
                    if(possibleField != null){
                        if(possibleField.isEmpty()){
                            field.addPossibleMoves(possibleField);
                        }else{
                            foundPiece=true;
                        }
                    }

                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-2);
                    if(possibleField != null && !foundPiece && possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                    // fields on upper corners
                    possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-1);
                    if(possibleField!=null && possibleField.getBlackPresent()){
                        field.addPossibleMoves(possibleField);
                    }
                    possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-1);
                    if(possibleField!=null && possibleField.getBlackPresent()){
                        field.addPossibleMoves(possibleField);
                    }
                }else{
                    // field 1 up
                    possibleField = getFieldByXY(field.getFigureX(),field.getFigureY()-1);
                    if(possibleField!=null && possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                    // fields on upper corners
                    possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-1);
                    if(possibleField!=null && possibleField.getBlackPresent()){
                        field.addPossibleMoves(possibleField);
                    }
                    possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-1);
                    if(possibleField!=null && possibleField.getBlackPresent()){
                        field.addPossibleMoves(possibleField);
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BPAWN){
                foundPiece = false;
                if(field.getFigureY()==1){
                    //2 fields to down
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()+1);
                    if(possibleField != null){
                        if(possibleField.isEmpty()){
                            field.addPossibleMoves(possibleField);
                        }else{
                            foundPiece=true;
                        }
                    }

                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()+2);
                    if(possibleField != null && !foundPiece && possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                    // fields on lower corners
                    possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+1);
                    if(possibleField!=null && possibleField.getWhitePresent()){
                        field.addPossibleMoves(possibleField);
                    }
                    possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+1);
                    if(possibleField!=null && possibleField.getWhitePresent()){
                        field.addPossibleMoves(possibleField);
                    }
                }else{
                    // field 1 down
                    possibleField = getFieldByXY(field.getFigureX(),field.getFigureY()+1);
                    if(possibleField!=null && possibleField.isEmpty()){
                        field.addPossibleMoves(possibleField);
                    }

                    // fields on lower corners
                    possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+1);
                    if(possibleField!=null && possibleField.getWhitePresent()){
                        field.addPossibleMoves(possibleField);
                    }
                    possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+1);
                    if(possibleField!=null && possibleField.getWhitePresent()){
                        field.addPossibleMoves(possibleField);
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.WBISHOP){
                foundPiece = false;
                // up left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // up right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() + i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX()+i, field.getFigureY()+i);
                    if(possibleField != null && possibleField.isEmpty() && !foundPiece){
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BBISHOP){
                foundPiece = false;
                // up left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // up right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() + i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX()+i, field.getFigureY()+i);
                    if(possibleField != null && possibleField.isEmpty() && !foundPiece){
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.WKNIGHT){
                foundPiece = false;

                possibleField = getFieldByXY(field.getFigureX()-2, field.getFigureY()-1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-2, field.getFigureY()+1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+2, field.getFigureY()-1);if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+2, field.getFigureY()+1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BKNIGHT){
                foundPiece = false;

                possibleField = getFieldByXY(field.getFigureX()-2, field.getFigureY()-1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-2, field.getFigureY()+1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+2, field.getFigureY()-1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+2, field.getFigureY()+1);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+2);
                if(possibleField != null && (possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.WROOK){
                foundPiece = false;
                // left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() - i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // down
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() + i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BROOK){
                foundPiece = false;
                // left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() - i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // down
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() + i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.WQUEEN){
                foundPiece = false;
                // left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() - i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // down
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() + i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // up right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() + i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX()+i, field.getFigureY()+i);
                    if(possibleField != null && possibleField.isEmpty() && !foundPiece){
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getBlackPresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BQUEEN){
                foundPiece = false;
                // left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY());
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() - i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // down
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX(), field.getFigureY() + i);
                    if (possibleField != null && !foundPiece && possibleField.isEmpty()) {
                        field.addPossibleMoves(possibleField);
                    }
                    if(possibleField != null && !possibleField.isEmpty()){
                        foundPiece = true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece = false;
                // up left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // up right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() + i, field.getFigureY() - i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down left
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX() - i, field.getFigureY() + i);
                    if (possibleField != null && possibleField.isEmpty() && !foundPiece) {
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
                foundPiece=false;
                // down right
                for(int i=1; i<8; i++) {
                    possibleField = getFieldByXY(field.getFigureX()+i, field.getFigureY()+i);
                    if(possibleField != null && possibleField.isEmpty() && !foundPiece){
                        field.addPossibleMoves(possibleField);
                    }else if(possibleField != null && !possibleField.isEmpty() && !foundPiece){
                        foundPiece=true;
                        if(possibleField.getWhitePresent()){
                            field.addPossibleMoves(possibleField);
                        }
                        break;
                    }
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.WKING){
                foundPiece = false;
                // up left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // up
                possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // up right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY());
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY());
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down
                possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getBlackPresent())){
                    field.addPossibleMoves(possibleField);
                }
            }

            if(field.getFigureType() == FieldPanel.FigureType.BKING){
                foundPiece = false;
                // up left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // up
                possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // up right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()-1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY());
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY());
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down left
                possibleField = getFieldByXY(field.getFigureX()-1, field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down
                possibleField = getFieldByXY(field.getFigureX(), field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
                }
                // down right
                possibleField = getFieldByXY(field.getFigureX()+1, field.getFigureY()+1);
                if(possibleField != null && ( possibleField.isEmpty() || possibleField.getWhitePresent())){
                    field.addPossibleMoves(possibleField);
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

    public HashMap<String, String> getHistory() {
        return history;
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
    JLabel figure;
    FigureType figureType;
    Integer figureX,figureY;
    Boolean whitePresent, blackPresent;
    ArrayList<FieldPanel> possibleMoves;
    Icon icon;
    JLabel selectedIcon;
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public FieldPanel(Integer figureX, Integer figureY){
        this.possibleMoves = new ArrayList<FieldPanel>();
        this.whitePresent = false;
        this.blackPresent = false;
        this.figureX = figureX;
        this.figureY = figureY;
        figure = new JLabel();
    }

    public void setSelected() {
        setBorder(BorderFactory.createLineBorder(new Color(193, 255, 179), 5));
    }
    public void setUnSelected() {
        setBorder(null);
    }

    public boolean isEmpty() {
        if (!this.getBlackPresent() && !this.getWhitePresent()) return true;
        else return false;
    }

    public void addPossibleMoves(FieldPanel possibleField) {
        getPossibleMoves().add(possibleField);
    }

    public boolean isPossibleMove() {
        if(getBorder() != null){
            if(getBorder().getBorderInsets(this).bottom==4){
                return true;
            }
        }
        return false;
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
        this.setIcon(figure.getIcon());
        if(this.figureType.toString().startsWith("W")){
            this.setWhitePresent(true);
            this.setBlackPresent(false);
        }
        if(this.figureType.toString().startsWith("B")){
            this.setBlackPresent(true);
            this.setWhitePresent(false);
        }
        this.add(this.figure);
    }

    public FigureType getFigureType() {
        return this.figureType;
    }

    public void removeFigure(){
        this.figureType=null;
        this.setIcon(null);
        remove(this.figure);
        validate();
        repaint();
        setWhitePresent(false);
        setBlackPresent(false);
    }

    public String toString(String figureType){
        String result = "";
        if(String.valueOf(figureType.charAt(1)).equals("K"))
            result = result.concat(String.valueOf(figureType.charAt(1)).concat(String.valueOf(figureType.charAt(2))));
        else if(!String.valueOf(figureType.charAt(1)).equals("P")){
            result = result.concat(String.valueOf(figureType.charAt(1)));
        }
        return result.concat(getCharForNumber(getFigureX()+1).concat(String.valueOf(8-getFigureY())));
    }
    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)).toLowerCase() : null;
    }
}