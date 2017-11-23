/**
 * DEVS-Suite Simulator
 * Arizona Center for Integrative Modeling & Simulation
 * Arizona State University, Tempe, AZ, USA
 *
 * Author(s): H.S. Sarjoughian & C. Zhang
 */

package LoadBalancer;

import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

import java.awt.*;

public class EF_Network extends ViewableDigraph {
	protected static int uniformNodes = 0;
	protected static int variableNodes = 1;
	protected static int roundRobinAlg = 0;
	protected static int randomAlg = 1;
	protected static int weightedRoundRobinAlg = 2;
	protected static int leastConnectionAlg = 3;
	protected static int uniformData = 0;
	protected static int variableData = 1;
	
    public EF_Network() {
        super("EF_Network");
        //##################################### Uniform node round robin algorithm of 100 uniform jobs
        ViewableDigraph ExpFrame = new ExpFrame(uniformData, 100);
        ViewableDigraph Network = new Network(uniformNodes, roundRobinAlg);
        //#####################################
        
        //##################################### Uniform node random algorithm of 100 uniform jobs
        //ViewableDigraph ExpFrame = new ExpFrame(uniformData, 100);
        //ViewableDigraph Network = new Network(uniformNodes, randomAlg);
        //#####################################
        
        //##################################### Uniform node weighted round robin algorithm of 100 uniform jobs
        //ViewableDigraph ExpFrame = new ExpFrame(uniformData, 100);
        //ViewableDigraph Network = new Network(uniformNodes, weightedRoundRobinAlg);
        //#####################################
        
        //##################################### Uniform node least connection algorithm of 100 uniform jobs
        //ViewableDigraph ExpFrame = new ExpFrame(uniformData, 100);
        //ViewableDigraph Network = new Network(uniformNodes, leastConnectionAlg);
        //#####################################
        
        add(ExpFrame);
        add(Network);
        addOutport("NumCompletedJobs");
        addOutport("NumJobsDropped");
        addOutport("AverageTimeNeeded");
        addCoupling(ExpFrame, "jobOut", Network, "jobIn");
        addCoupling(Network, "jobOut", ExpFrame, "jobIn");
        addCoupling(ExpFrame, "NumCompletedJobs", this, "NumCompletedJobs");
        addCoupling(ExpFrame, "NumJobsDropped", this, "NumJobsDropped");
        addCoupling(ExpFrame, "AverageTimeNeeded", this, "AverageTimeNeeded");
    }

    //=============================================================================================
    public void layoutForSimView() {
        preferredSize = new Dimension(1223, 507);
        ((ViewableComponent) withName("ExpFrame")).setPreferredLocation(new Point(39, 54));
        ((ViewableComponent) withName("Network")).setPreferredLocation(new Point(736, 223));
    }
}
