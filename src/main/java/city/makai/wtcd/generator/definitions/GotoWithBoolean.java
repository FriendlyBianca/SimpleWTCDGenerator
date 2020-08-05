package city.makai.wtcd.generator.definitions;

public class GotoWithBoolean extends Action {
	BooleanVariable variable;
	public Action whenTrue;
	public Action whenFalse;

	public GotoWithBoolean(BooleanVariable variable, Action whenTrue, Action whenFalse) {
		this.variable = variable;
		this.whenTrue = whenTrue;
		this.whenFalse = whenFalse;
	}

	@Override
	public String renderAction() {
		if (whenTrue == null) {
			whenTrue = new Exit();
		}
		if (whenFalse == null) {
			whenFalse = new Exit();
		}
		return "( " + variable.getName() + " ? " + whenTrue.renderAction() + " : " + whenFalse.renderAction()
				+ " )";
	}
}
