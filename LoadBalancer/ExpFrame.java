package LoadBalancer;

import GenCol.Queue;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

import java.awt.*;

public class ExpFrame extends ViewableDigraph {
    public ExpFrame() {
        super("ExpFrame");

        double expTime = 80.0;
        ViewableAtomic Generator = new Generator();
        ViewableAtomic Transducer = new Transducer(expTime);
        add(Generator);
        add(Transducer);
        addInport("jobIn");
        addOutport("jobOut");
        addOutport("NumCompletedJobs");
        addOutport("NumJobsDropped");
        addOutport("AverageTimeNeeded");
        addCoupling(this, "jobIn", Transducer, "JobCompleted");
        addCoupling(Transducer, "Stop", Generator, "Stop");
        addCoupling(Generator, "jobOut", Transducer, "JobArrived");
        addCoupling(Generator, "jobOut", this, "jobOut");
        addCoupling(Transducer, "NumCompletedJobs", this, "NumCompletedJobs");
        addCoupling(Transducer, "NumJobsDropped", this, "NumJobsDropped");
        addCoupling(Transducer, "AverageTimeNeeded", this, "AverageTimeNeeded");
    }

    //=============================================================================================
    public void layoutForSimView() {
        preferredSize = new Dimension(625, 419);
        ((ViewableComponent) withName("Generator")).setPreferredLocation(new Point(4, 38));
        ((ViewableComponent) withName("Transducer")).setPreferredLocation(new Point(58, 223));
    }
}
