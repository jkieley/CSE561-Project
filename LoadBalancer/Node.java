package LoadBalancer;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;
import GenCol.*;


public class Node extends ViewableAtomic 
	{
	protected entity job;
	protected double CPUCapacity;
	protected double MemoryCapacity;
	protected double DBConnectionCapacity;
	protected Queue connectionList;
	
//=============================================================================================
	public Node(String name, double cpu, double memory, double dbConnections) 
		{
		super(name);
		CPUCapacity = cpu;
		MemoryCapacity = memory;
		DBConnectionCapacity = dbConnections;
		connectionList = new Queue();
		addInport("jobIn");
		addOutport("jobOut");
		addTestInput("jobIn", new Job("job0", 5, 5, 10, true));
		initialize();
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
						Job processJob = (Job)x.getValOnPort("jobIn", i);
						if(processJob.getProcessor().equals(this.name))
							{
							job = x.getValOnPort("jobIn", i);
							double cpuToUse = processJob.getCPUNeeded();
							double memoryToUse = processJob.getMemoryNeeded();
							int connection = 0;
							if(processJob.isConnectionNeeded())
								connection = 1;
							CPUCapacity = CPUCapacity - cpuToUse;
							MemoryCapacity = MemoryCapacity - memoryToUse;
							DBConnectionCapacity = DBConnectionCapacity - connection;
							connectionList.add(job);
							holdIn("Working", processJob.getTimeNeeded());
							}
						}
					}
				}
			else if (phaseIs("Working"))
				{
				for (int i = 0; i < x.getLength(); i++)
					{
					if (messageOnPort(x, "jobIn", i)) 
						{
						Job processJob = (Job)x.getValOnPort("jobIn", i);
						if(processJob.getProcessor().equals(this.name))
							{
							double cpuToUse = processJob.getCPUNeeded();
							double memoryToUse = processJob.getMemoryNeeded();
							int connection = 0;
							if(processJob.isConnectionNeeded())
								connection = 1;
							if(CPUCapacity - cpuToUse >= 0 && MemoryCapacity - memoryToUse >= 0 && DBConnectionCapacity - connection >= 0)
								{
								CPUCapacity = CPUCapacity - cpuToUse;
								MemoryCapacity = MemoryCapacity - memoryToUse;
								DBConnectionCapacity = DBConnectionCapacity - connection;
								connectionList.add(processJob);
								}
							}
						}
					}
				}
			}
//=============================================================================================
		public void deltint() 
			{
			Job processJob = (Job)connectionList.removeFirst();
			double cpuUsed = processJob.getCPUNeeded();
			double memoryUsed = processJob.getMemoryNeeded();
			int connection = 0;
			if(processJob.isConnectionNeeded())
				connection = 1;
			CPUCapacity = CPUCapacity + cpuUsed;
			MemoryCapacity = MemoryCapacity + memoryUsed;
			DBConnectionCapacity = DBConnectionCapacity - connection;
			if(connectionList.isEmpty())
				passivateIn("Waiting");
			else
				{
				job = (entity)connectionList.first();
				processJob = (Job)job;
				holdIn("Working", processJob.getTimeNeeded());
				}
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
