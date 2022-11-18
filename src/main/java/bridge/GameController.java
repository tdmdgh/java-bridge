package bridge;

import java.util.List;

/**
 * 게임의 진행을 관리하는 역할을 한다.
 */
public class GameController {
    private OutputView outputView =new OutputView();
    private InputView inputView = new InputView();
    private BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
    public void begin(){
        int trials = 1;
        outputView.gameStartMessage();
        outputView.bridgeSizeMessage();

        int size = inputView.readBridgeSize();

        List<String> answer_bridge = bridgeMaker.makeBridge(size);
        System.out.println(answer_bridge);

        BridgeGame bridgeGame = new BridgeGame(answer_bridge);
        boolean keepGoing = true;
        while(keepGoing) {
            boolean isEnd = false;
            while (!isEnd) {
                moveStep(bridgeGame);
                isEnd = bridgeGame.isEnd();
            }
            if (bridgeGame.isSuccess()) {
                outputView.printResult("성공", bridgeGame, trials);
                return;
            }
            outputView.restartMessage();
            String command = inputView.readGameCommand();
            if (command.equals("R"))
                bridgeGame.retry();

            if (command.equals("Q")) {
                outputView.printResult("실패", bridgeGame, trials);
                return;
            }
            trials++;
        }
    }
    private void moveStep(BridgeGame bridgeGame){
        //move message 출력
        outputView.moveMessage();
        //input move
        String way = inputView.readMoving();
        //move
        bridgeGame.move(way);
        //printMap
        outputView.printMap(bridgeGame);

    }
}
