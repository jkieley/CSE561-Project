package LoadBalancer;
import GenCol.*;
import model.modeling.*;
import view.modeling.ViewableAtomic;

public class Generator extends ViewableAtomic 
	{
	protected int count;
	protected Queue TAs;
	protected Queue jobs;
//=============================================================================================
	public Generator(Queue taList, Queue jobList) 
		{
		super("Generator");
		addInport("Stop");
		jobs = jobList;
		TAs = taList;
		addOutport("jobOut");
		initialize();
		}
//=============================================================================================
	public void initialize() 
		{
		holdIn("active", 0);
		count = 0;
		super.initialize();
		}
//=============================================================================================
	public void deltext(double e, message x) 
		{
		Continue(e);
		passivateIn("Stopped");
		}
//=============================================================================================
	public void deltint() 
		{
		if (phaseIs("active") && !TAs.isEmpty()) 
			{
			holdIn("active", (double)TAs.removeFirst());
			count = count + 1;
			}
		else
			passivateIn("Stopped");
		}
//=============================================================================================
	public message out()
		{
		message m = new message();
		content con = makeContent("jobOut", (entity)jobs.removeFirst());
		m.add(con);
		return m;
		}
//=============================================================================================
	public void showState()
		{
		super.showState();
		}
//=============================================================================================
	public String getTooltipText() 
		{
		return super.getTooltipText() + "\n"  + " count: " + count;
		}

	}