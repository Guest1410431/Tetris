package Command;
//The command interface allows all other commands to exist
public interface Command
{
	public void execute();

	public void unexecute();
}
