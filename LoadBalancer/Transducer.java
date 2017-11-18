package LoadBalancer;

import GenCol.doubleEnt;
import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

import java.util.HashMap;
import java.util.Map;


public class Transducer extends ViewableAtomic {
    public Double count = 0.00;
    protected Map jobsArrived;
    protected Map jobsCompleted;
    protected double clock;
    protected double total_ta;
    protected double observation_time;

    public Transducer() {
        this(200);
    }

    //===============================================================================
    public Transducer(double taObservation) {
        super("Transducer");
        addInport("JobArrived");
        addInport("JobCompleted");
        addOutport("NumJobsDropped");
        addOutport("NumCompletedJobs");
        addOutport("AverageTimeNeeded");
        addOutport("Stop");
        jobsArrived = new HashMap();
        jobsCompleted = new HashMap();
        observation_time = taObservation;
        addTestInput("JobArrived", new Job("job0", 5, 5, 10, true));
        addTestInput("JobCompleted", new Job("job0", 5, 5, 10, true));
        initialize();
    }

    //===============================================================================
    public void initialize() {
        phase = "active";
        sigma = observation_time;
        clock = 0;
        total_ta = 0;
        super.initialize();
    }

    //===============================================================================
    public void showState() {
        super.showState();
        System.out.println("jobs arrived: " + jobsArrived.size());
        System.out.println("jobs completed: " + jobsCompleted.size());
    }

    //===============================================================================
    public void deltext(double e, message x) {
        System.out.println("--------Transduceer elapsed time =" + e);
        System.out.println("-------------------------------------");
        clock = clock + e;
        Continue(e);
        entity val;
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "JobArrived", i)) {
                val = x.getValOnPort("JobArrived", i);
                jobsArrived.put(val.getName(), new doubleEnt(clock));
            } else if (messageOnPort(x, "JobCompleted", i)) {
                val = x.getValOnPort("JobCompleted", i);
                if (jobsArrived.containsKey(val.getName())) {
                    entity ent = (entity) jobsArrived.get(val.getName());
                    doubleEnt num = (doubleEnt) ent;
                    double arrival_time = num.getv();
                    double turn_around_time = clock - arrival_time;
                    total_ta = total_ta + turn_around_time;
                    jobsCompleted.put(val.getName(), new doubleEnt(clock));
                }
            }
        }
        show_state();
    }

    //===============================================================================
    public void deltint() {
        clock = clock + sigma;
        passivate();
        show_state();
    }

    //===============================================================================
    public message out() {
        message m = new message();
        double average;
        if (jobsCompleted.size() == 0)
            average = 0;
        else
            average = total_ta / jobsCompleted.size();
        content con1 = makeContent("AverageTimeNeeded", new entity(" " + average));
        content con2 = makeContent("NumCompletedJobs", new entity("" + jobsCompleted.size()));
        content con3 = makeContent("NumJobsDropped", new entity("" + (jobsArrived.size() - jobsCompleted.size())));
        content con4 = makeContent("Stop", new entity("Stop"));
        m.add(con1);
        m.add(con2);
        m.add(con3);
        m.add(con4);
        return m;
    }

    //===============================================================================
    public void show_state() {
        System.out.println("state of  " + name + ": ");
        System.out.println("phase, sigma : "
                + phase + " " + sigma + " ");

        if (jobsArrived != null && jobsCompleted != null) {
            System.out.println("jobs arrived :");
            System.out.println("total :" + jobsArrived.size());
            System.out.println("jobs completed :");
            System.out.println("total :" + jobsCompleted.size());
        }
    }
}
