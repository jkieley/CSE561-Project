package LoadBalancer;
import GenCol.entity;

public class Job extends entity 
	{
	protected double CPU;
	protected double Memory;
	protected double ProcessingTime;
	protected boolean DBConnection;
	protected String node;
	
	Job(String name, double CPUUsage, double MemoryUsage, double timeNeeded, boolean connectionNeeded)
		{
		super.name = name;
		CPU = CPUUsage;
		Memory = MemoryUsage;
		ProcessingTime = timeNeeded;
		DBConnection = connectionNeeded;
		node = "";
		}
//=============================================================================================================	
	public double getCPUNeeded() 
		{
		return CPU;
		}
//=============================================================================================================	
	public double getMemoryNeeded() 
		{
		return Memory;
		}
//=============================================================================================================	
	public double getTimeNeeded() 
		{
		return ProcessingTime;
		}
//=============================================================================================================	
	public boolean isConnectionNeeded() 
		{
		return DBConnection;
		}
//=============================================================================================================	
	public void setNode(String name) 
		{
		node = name;
		}
//=============================================================================================================	
	public String getProcessor() 
		{
		return node;
		}
//=============================================================================================================		
	}
