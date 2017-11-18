package LoadBalancer;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;
import GenCol.*;


public class LoadBalancer extends ViewableAtomic 
	{
	protected entity job;
	protected Queue nodes;
	
//=============================================================================================
	public LoadBalancer(Queue nodeList) 
		{
		super("LoadBalancer");
		nodes = nodeList;
		addInport("jobIn");
		addOutport("jobOut");
		addTestInput("jobIn", new Job("job0", 5, 5, 10, true));
		}
//=============================================================================================
		public void initialize() 
			{
			passivateIn("Waiting");
			super.initialize();
			}
//=============================================================================================
		public void deltext(double e, message x) 
			{
			Continue(e);
			if (phaseIs("Waiting"))
				{
				for (int i = 0; i < x.getLength(); i++)
					{
					if (messageOnPort(x, "jobIn", i)) 
						{	
						job = x.getValOnPort("jobIn", i);
						((Job)job).setNode(chooseNode());
						holdIn("Delegating",0);
						}
					}
				}
			}
//=============================================================================================
		public String chooseNode() 
			{
			String node = "";
			
			return node;
			}
//=============================================================================================
		public void deltint() 
			{
			passivateIn("Waiting");
			}
//=============================================================================================
		public void deltcon(double e, message msg)
			{
			deltint ( );
			deltext (0, msg);
			}
//=============================================================================================
		public message out()
			{
			message m = new message();
			m.add(makeContent("jobOut", job));
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
			return super.getTooltipText() + "\n" + job.getName();
			}
	}
