package org.seariver.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.seariver.BaseActor;
import org.seariver.BaseGame;
import org.seariver.BaseScreen;
import org.seariver.actor.PuzzleArea;
import org.seariver.actor.PuzzlePiece;

public class LevelScreen extends BaseScreen {

    private Label messageLabel;

    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("background.jpg");

        int numberRows = 3;
        int numberCols = 3;

        Texture texture = new Texture(Gdx.files.internal("sun.jpg"), true);
        int imageWidth = texture.getWidth();
        int imageHeight = texture.getHeight();
        int pieceWidth = imageWidth / numberCols;
        int pieceHeight = imageHeight / numberRows;

        TextureRegion[][] temp = TextureRegion.split(texture, pieceWidth, pieceHeight);

        for (int row = 0; row < numberRows; row++) {
            for (int col = 0; col < numberCols; col++) {

                // create puzzle piece at random location on left half of screen
                int pieceX = MathUtils.random(0, 400 - pieceWidth);
                int pieceY = MathUtils.random(0, 600 - pieceHeight);

                PuzzlePiece puzzlePiece = new PuzzlePiece(pieceX, pieceY, mainStage);
                Animation<TextureRegion> anim = new Animation<>(1, temp[row][col]);
                puzzlePiece.setAnimation(anim);
                puzzlePiece.setRow(row);
                puzzlePiece.setCol(col);

                // create puzzle area on right half of screen
                int marginX = (400 - imageWidth) / 2;
                int marginY = (600 - imageHeight) / 2;

                int areaX = (400 + marginX) + pieceWidth * col;
                int areaY = (600 - marginY - pieceHeight) - pieceHeight * row;

                PuzzleArea puzzleArea = new PuzzleArea(areaX, areaY, mainStage);
                puzzleArea.setSize(pieceWidth, pieceHeight);
                puzzleArea.setBoundaryRectangle();
                puzzleArea.setRow(row);
                puzzleArea.setCol(col);
            }
        }

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);

        uiTable.add(messageLabel).expandX().expandY().bottom().pad(50);
        messageLabel.setVisible(false);
    }

    public void update(float deltaTime) {

        boolean solved = true;

        for (BaseActor actor : BaseActor.getList(mainStage, "org.seariver.actor.PuzzlePiece")) {
            PuzzlePiece puzzlePiece = (PuzzlePiece) actor;
            if (!puzzlePiece.isCorrectlyPlaced())
                solved = false;
        }

        if (solved) {
            messageLabel.setText("You win!");
            messageLabel.setVisible(true);
        } else {
            messageLabel.setText("...");
            messageLabel.setVisible(false);
        }
    }
}
