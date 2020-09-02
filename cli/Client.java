package cli;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import core.Clock;
import core.Loader;
import core.Project;
import core.Saver;
import core.Task;
import report.BriefReport;
import report.Period;
import report.Report;
import report.format.Format;
import report.format.FormatHTML;

/**
 * Clase cliente que permite ejecutar el programa y realizar
 * tests basicos
 */
public class Client {

    public static void main(String[]args){
        Client client = new Client();
        client.run();
    }

    public void run() {

        //testA1();
        //testA2();
       // testOwn();
       //testSerializable();

        testReport();
    }

    /**
     * Metodo que implementa el test A1
     */
    public void testA1(){
        Clock clock = Clock.getInstance();
        clock.setTimeNotify(2);
        clock.setMinimTime(2);


        Project pr1 = new Project("P1",null);
        Project pr2 = new Project("P2",pr1);


        Task task3 = new Task("T3",pr1);
        Task task1 = new Task("T1",pr2);
        Task task2 = new Task("T2",pr2);

        pr1.addActivity(task3);
        pr1.addActivity(pr2);
        pr2.addActivity(task1);
        pr2.addActivity(task2);

        View view = new View(2,pr1);
        clock.start();
        task3.start();

        timeToWait(3);
        task3.stop();

        timeToWait(7);

        task2.start();
        timeToWait(10);
        task2.stop();

        task3.start();
        timeToWait(2);
        task3.stop();


        clock.setStop(true);



    }

/**
 * Metodo que implementa el test A2
 */
    public void testA2(){
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Clock clock = Clock.getInstance();
        clock.setTimeNotify(2);
        clock.setMinimTime(2);


        Project pr1 = new Project("P1",null);
        Project pr2 = new Project("P2",pr1);


        Task task3 = new Task("T3",pr1);
        Task task1 = new Task("T1",pr2);
        Task task2 = new Task("T2",pr2);


        pr1.addActivity(task3);
        pr1.addActivity(pr2);
        pr2.addActivity(task1);
        pr2.addActivity(task2);

        clock.start();
        task3.start();

        View view = new View(2,pr1);

        timeToWait(4);
        task2.start();
        timeToWait(2); /* este tiempo en la task3 no lo cuenta */

        task3.stop();
        System.out.println("2 ---> "+formatTime.format(task2.getTimeActivity().getTime()));
        System.out.println("6 --->"+formatTime.format(task3.getTimeActivity().getTime()));

        timeToWait(2);
        task1.start();
        timeToWait(4);
        task1.stop();
        timeToWait(2);
        task2.stop();
        timeToWait(4);

        task3.start();
        timeToWait(2);

        task3.stop();
        timeToWait(4);

        clock.setStop(true);
    }

/**
 * Metodo que implementa un test propio
 */
    public void testOwn(){
        Clock clock = Clock.getInstance();
        clock.setTimeNotify(1);
        clock.setMinimTime(2);


        Project pr1 = new Project("P1",null);
        Project pr2 = new Project("P2",pr1);


        Task task3 = new Task("T3",pr1);
        Task task1 = new Task("T1",pr2);
        Task task2 = new Task("T2",pr2);


        pr1.addActivity(task3);
        pr1.addActivity(pr2);
        pr2.addActivity(task1);
        pr2.addActivity(task2);

        clock.start();
        task3.start();

        View view = new View(2,pr1);

        timeToWait(4);
        task3.stop();

        task3.start();
        timeToWait(2); /* este tiempo en la task3 no lo cuenta */
        task3.stop();

        clock.setStop(true);
    }

    /**
     * Metodo que implementa un test para comprobar la serializacion: clases saver y loader
     */

    public void testSerializable(){
        Clock clock = Clock.getInstance();
        clock.setTimeNotify(2);
        clock.setMinimTime(2);

        Project pr1 = new Project("P1",null);

        Project pr2 = new Project("P2",pr1);

        Task task3 = new Task("T3",pr1);
        Task task1 = new Task("T1",pr2);
        Task task2 = new Task("T2",pr2);

        pr1.addActivity(task3);
        pr1.addActivity(pr2);
        pr2.addActivity(task1);
        pr2.addActivity(task2);

        clock.start();

        task1.start();
        task2.start();
        /*
        task3.start();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task3.stop();
        */

        timeToWait(3);

        Saver saver = new Saver("Project.dat");
        saver.saveProject(pr2);

        //task3.stop();

        Loader loader = new Loader("Project.dat");
        Project p2B = loader.loadProject();
        View view = new View(   2 ,p2B);
        Task task1B = new Task("T1B",p2B);
        p2B.addActivity(task1B);
        task1B.start();
    }
    
    /**
     * Metodo que permite simular el paso del tiempo y que imprime cuantos segundos han pasado
     * @param time cantidad de tiempo que queremos que pase
     */
    public void timeToWait(int time){
        try {
            Thread.sleep(time*1000);
            System.out.println("> Pasan "+time+" segundos.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void testReport(){

        Clock clock = Clock.getInstance();
        clock.setTimeNotify(2);
        clock.setMinimTime(2);

        Project pr1 = new Project("P1",null);
        Task t1 = new Task("T1",pr1);
        Task t2 = new Task("T2",pr1);
        Project pr3 = new Project("P3",pr1);

        pr1.addActivity(t1);
        pr1.addActivity(t2);
        pr1.addActivity(pr3);

        Task t4 = new Task("T4",pr3);
        pr3.addActivity(t4);

        Project pr2 = new Project("P2",null);
        Task t3 = new Task("T3",pr2);
        pr2.addActivity(t3);

        View view = new View(2,pr1);
        clock.start();


        t1.start();
        t4.start();
        timeToWait(4);
        t1.stop();
        t2.start();
        timeToWait(6);
        t2.stop();
        t4.stop();
        t3.start();
        timeToWait(4);
        t3.stop();
        t2.start();
        timeToWait(2);
        t3.start();
        timeToWait(4);
        t2.stop();
        t3.stop();


        clock.setStop(true);


        /* ------ Creating report ------- */
        List<Project> projects = new ArrayList<>();
        projects.add(pr1);
        projects.add(pr3);

        Report report = new BriefReport(projects, new Period(Calendar.getInstance(),Calendar.getInstance()));

        Format format = new FormatHTML();
        report.buildReport();
        report.write(format);

    }


}
