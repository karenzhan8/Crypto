package utils;

public class UpdateData extends Subject{
	public void tick(UserSelection selection, ExecuteTrade cumulativeTrades) {
		notifyObservers(selection, cumulativeTrades);
	}
}
