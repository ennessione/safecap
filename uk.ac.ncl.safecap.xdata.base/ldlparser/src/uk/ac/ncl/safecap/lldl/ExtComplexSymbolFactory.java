package uk.ac.ncl.safecap.lldl;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;


public class ExtComplexSymbolFactory extends ComplexSymbolFactory {
	public ExtComplexSymbolFactory() {
		super();
	}

	public Symbol newSymbolX(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, Object value) {
		return super.newSymbol(name, id, left, right, value);
	} 

	/*
	@Override
	public Symbol newSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, Object value) {
		Symbol s = super.newSymbol(name, id, left, right, value);
		syntree ast = (syntree) value;
		ast.setAbsolutePosition(left.getLine(), left.getColumn());	
		ast.setLineColPosition(right.getLine(), right.getColumn());	
		return s;
	} 
	*/

	@Override
	public Symbol newSymbol(String name, int id, Symbol _left, Symbol _right, Object value) {
		ComplexSymbolFactory.ComplexSymbol sleft = (ComplexSymbolFactory.ComplexSymbol) _left;
		ComplexSymbolFactory.ComplexSymbol sright = (ComplexSymbolFactory.ComplexSymbol) _right;

		Symbol s = super.newSymbol(name, id, sleft, sright, value);


		if (value != null && sleft != null && sright != null && 
		   sleft.getLeft() != null && sleft.getRight() != null &&
		   sright.getLeft() != null && sright.getRight() != null) {
			ComplexSymbolFactory.Location left1 = sleft.getLeft();
			ComplexSymbolFactory.Location right1 = sleft.getRight();

	
			ComplexSymbolFactory.Location left2 = sright.getLeft();
			ComplexSymbolFactory.Location right2 = sright.getRight();

			syntree ast = (syntree) value;

			ast.setAbsolutePosition(left1.getLine(), left1.getColumn());	
			ast.setLineColPosition(right1.getLine(), right1.getColumn());	
			ast.setAbsolutePosition(left2.getLine(), left2.getColumn());	
			ast.setLineColPosition(right2.getLine(), right2.getColumn());	
		}

		return s;
	} 
}

