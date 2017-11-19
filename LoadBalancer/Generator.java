package LoadBalancer;

import GenCol.Queue;
import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

import java.util.LinkedList;

public class Generator extends ViewableAtomic {
    protected int count;
    protected double ta;
    protected Queue jobs;

    //=============================================================================================
    public Generator() {
        super("Generator");
        addInport("Stop");
        jobs = new Queue();
        addOutport("jobOut");
        initialize();
    }

    //=============================================================================================
    public void initialize() {
        holdIn("active", 0);
        count = 0;
        super.initialize();

        LinkedList<Job> jobs = JobData.get();
        for (Job job : jobs) {
            this.jobs.push(job);
        }

        ta = 30.00 / jobs.size();

    }

    //=============================================================================================
    public void deltext(double e, message x) {
        Continue(e);
        passivateIn("Stopped");
    }

    //=============================================================================================
    public void deltint() {
        if (phaseIs("active")) {
            holdIn("active", ta);
            count = count + 1;
        } else
            passivateIn("Stopped");
    }

    //=============================================================================================
    public message out() {
        message m = new message();
        content con = makeContent("jobOut", (entity) jobs.removeFirst());
        m.add(con);
        return m;
    }

    //=============================================================================================
    public void showState() {
        super.showState();
    }

    //=============================================================================================
    public String getTooltipText() {
        return super.getTooltipText() + "\n" + " count: " + count;
    }

}