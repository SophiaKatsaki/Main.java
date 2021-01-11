import java.util.Scanner;

/**
 * In this class exists the view of the game and the interface with the user(s)/ player(s).
 * The interface with the user(s)/ player(s) starts from the beginning through messages which are being displayed on the
 * screen for the whole the duration.
 *
 * It stars by creating an object from the class Game, an object from the class Statistics and two objects from the
 * class Player for the providing modes "Solo Game" and "Game With a Friend".
 * Then it asks whether the user(s)/ player(s) wants to know the Statistics for any mode of the game.
 * Afterwards, it asks the number of the players and setting the mode of the game and the name/s of the player/s.
 * That four objects get destroyed when the game ends and the program closes.
 * The next object that is being created is the object from the class Round.
 * That object gets destroyed everytime one round ends and gets created again when a new round begins.
 * Sooner than later, objects from the classes Question and Answer get created.
 * At the same time, the abstract class QA which has all the important data for the classes Question and Answer is
 * being called by them.
 * Both objects get destroyed everytime a question ends and get created again when a new question begins.
 *
 * In the end, there exists some last messages that make the last interface with the user/ player.
 * These messages inform them about the end of the game.
 * Before the program ends it refreshes the statistics depending on the performance of the one player or the players
 * and asks for one last time if the user(s)/ player(s) wants to know the Statistics for any mode of the game and if
 * they do not want it ends the game, the main class and, at the same time the view.
 *
 * @author Sophia Katsaki
 * @author Stylianos Tozios
 */


public class Main
{
    public static void main(String[] args)
    {
        Game game = new Game();
        Statistics statistics = new Statistics() {};
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        System.out.println("Hello and welcome to the best game ever,Buzz!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Statistics: ");
        System.out.println("Select: ");
        System.out.println("1) Solo Game.");
        System.out.println("2) Play with a friend.");
        System.out.println("3) Don't want Statistics.");
        statistics.makeStatistics(scanner.nextInt());

        scanner = new Scanner(System.in);
        System.out.println("Let's begin!");
        System.out.println("Select Mode: ");
        System.out.println("1) Solo Game.");
        System.out.println("2) Play with a friend.");
        game.setAnswer(scanner.nextInt());

        System.out.println("Enter name:");
        scanner = new Scanner(System.in);
        player1.setName(scanner.nextLine());
        System.out.println("Welcome " + player1.getName() + "!");

        if (game.getAnswer() == 2) {
            game.setNumberOfPlayers(2);
            System.out.println("Enter name:");
            scanner = new Scanner(System.in);
            player2.setName(scanner.nextLine());
            System.out.println("Welcome " + player2.getName() + "!");
        }

        System.out.println("Ready to play buzz?");

        System.out.println("Select:");
        System.out.println("1) Choose the number of rounds.");
        System.out.println("2) Random rounds.");
        scanner = new Scanner(System.in);
        game.setAnswer(scanner.nextInt());

        if (game.getAnswer()==1) {
            System.out.println("From 1 to 10,");
            System.out.println("how many rounds do you want to play?");
            game.setNumberOfRounds(scanner.nextInt());
            System.out.println("You choose "+ game.getNumberOfRounds()+ " round/s.");
        }
        else {
            game.setRandomRounds();
            System.out.println(game.getNumberOfRounds()+" round/s was chosen.");
        }

        System.out.println(player1.getName() + ", you select an answer between 1 and 4!");
        if (game.getNumberOfPlayers() == 2) {
            System.out.println(player2.getName() + ", you select an answer between 6 and 9!");
        }

        while (game.getCurrentRound() <= game.getNumberOfRounds()) {
            Round round = new Round();

            System.out.println("Round " + game.getCurrentRound());
            if (game.getCurrentRound() == game.getNumberOfRounds()) {
                System.out.println("Last Round");
            }

            System.out.println(round.getKind());

            while (round.getCurrentNumberOfQuestion() <= round.getRandomNumberOfQuestions()) {
                Question question = new Question();

                question.setRandomQuestion();
                question.setNumberOfRandomQuestionInArray();

                Answer answer1 = new Answer();
                Answer answer2 = new Answer();

                System.out.println("Question " + round.getCurrentNumberOfQuestion());
                if (round.getKind().equals("Bet")) {
                    System.out.println("\"" + question.getCategory() + "\"");
                    System.out.println(player1.getName() + ": Bet 250, 500, 750 or 1000");
                    scanner = new Scanner(System.in);
                    player1.setBetPoints(scanner.nextInt());
                    if (game.getNumberOfPlayers() == 2) {
                        System.out.println(player2.getName() + ": Bet 250, 500, 750 or 1000");
                        scanner = new Scanner(System.in);
                        player2.setBetPoints(scanner.nextInt());
                    }
                }

                System.out.println(question.getRandomQuestion());

                answer1.setFourPossibleAnswers(question.getNumberOfRandomQuestionInArray(),
                        question.getNumberOfCategory());
                if (game.getNumberOfPlayers() == 2) {
                    answer2.setFourPossibleAnswers(question.getNumberOfRandomQuestionInArray(),
                            question.getNumberOfCategory());
                }

                answer1.showAnswers(player1.getPlayerNumber());
                if (game.getNumberOfPlayers() == 2) {
                    answer2.showAnswers(player2.getPlayerNumber());
                }

                System.out.println("Select an answer:");
                player1.setNumberOfAnswer(scanner.nextInt());
                answer1.setCorrectAnswer(player1.getNumberOfAnswer(), question.getNumberOfRandomQuestionInArray());

                if (answer1.getCorrectAnswer()) {
                    if(round.getKind().equals("Bet")){
                        player1.winPoints(player1.getBetPoints());
                        System.out.println(player1.getName() + " +" + player1.getBetPoints());
                    }
                    else
                    {
                        player1.winPoints(1000);
                        System.out.println(player1.getName() + " +1000");
                    }
                }

                if ((round.getKind().equals("Bet")) && (!(answer1.getCorrectAnswer()))) {
                    player1.losePoints(player1.getBetPoints());
                    System.out.println(player1.getName() + " -" + player1.getBetPoints());
                }

                if (game.getNumberOfPlayers()==2) {
                    scanner = new Scanner(System.in);
                    player2.setNumberOfAnswer(scanner.nextInt());
                    answer2.setCorrectAnswer(player2.getNumberOfAnswer(), question.getNumberOfRandomQuestionInArray());

                    if (answer2.getCorrectAnswer()) {
                        if(round.getKind().equals("Bet")){
                            player2.winPoints(player2.getBetPoints());
                            System.out.println(player2.getName() + " +" + player2.getBetPoints());
                        }
                        else
                        {
                            player2.winPoints(1000);
                            System.out.println(player2.getName() + " +1000");
                        }
                    }

                    if ((round.getKind().equals("Bet")) && (!(answer2.getCorrectAnswer()))) {
                        player2.losePoints(player2.getBetPoints());
                        System.out.println(player2.getName() + " -" + player2.getBetPoints());
                    }
                }

                round.setCurrentNumberOfQuestion();

                question.questions.clear();
                question.categories.clear();
                question.possibleAnswers.clear();
            }

            System.out.println(player1.getName() + ", you have " + player1.getPoints() + " points.");
            if (game.getNumberOfPlayers() == 2) {
                System.out.println(player2.getName() + ", you have " + player2.getPoints() + " points.");
            }

            game.setCurrentRound();
        }

        System.out.println("The game is over.");

        if (game.getNumberOfPlayers() == 1) {
            System.out.println(player1.getName() + ", you got " + player1.getPoints() + " points.");
            statistics.refreshSoloGame(statistics.getOldStatistics(), player1.getName(), player1.getPoints());
        }
        else if (game.getNumberOfPlayers()==2) {
            System.out.println(player2.getName() + ", you got " + player2.getPoints() + " points.");

            if (player1.getPoints() > player2.getPoints()) {
                System.out.println(player1.getName() + ", you won!");
                statistics.refreshMultiplayer(statistics.getOldStatistics(), player1.getName(),
                        player2.getName(), player1.getPoints(), player2.getPoints());
            }
            else if (player1.getPoints() < player2.getPoints()) {
                System.out.println(player2.getName() + ", you won!");
                statistics.refreshMultiplayer(statistics.getOldStatistics(), player2.getName(),
                        player1.getName(), player2.getPoints(), player1.getPoints());
            }
            else {
                System.out.println("Tie!");
                statistics.refreshMultiplayer(statistics.getOldStatistics(), player1.getName(),
                        player2.getName(), player1.getPoints(), player2.getPoints());
            }
        }

        scanner = new Scanner(System.in);
        System.out.println("Statistics: ");
        System.out.println("Select: ");
        System.out.println("1) Solo Game.");
        System.out.println("2) Play with a friend.");
        System.out.println("3) Don't want Statistics.");
        statistics.makeStatistics(scanner.nextInt());

        System.out.println("See you next time!");
    }
}