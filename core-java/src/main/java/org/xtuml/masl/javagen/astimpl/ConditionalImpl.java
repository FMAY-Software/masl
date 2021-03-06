//
// UK Crown Copyright (c) 2011. All Rights Reserved.
//
package org.xtuml.masl.javagen.astimpl;

import org.xtuml.masl.javagen.ast.ASTNodeVisitor;
import org.xtuml.masl.javagen.ast.expr.Conditional;
import org.xtuml.masl.javagen.ast.expr.Expression;


class ConditionalImpl extends ExpressionImpl
    implements Conditional
{

  ConditionalImpl ( final ASTImpl ast,
                                     final Expression condition,
                                     final Expression trueValue,
                                     final Expression falseValue )
  {
    super(ast);
    setCondition(condition);
    setTrueValue(trueValue);
    setFalseValue(falseValue);
  }

  @Override
  public <R, P> R accept ( final ASTNodeVisitor<R, P> v, final P p ) throws Exception
  {
    return v.visitConditional(this, p);
  }

  @Override
  public ExpressionImpl getCondition ()
  {
    return condition.get();
  }

  @Override
  public ExpressionImpl getFalseValue ()
  {
    return falseValue.get();
  }

  @Override
  public ExpressionImpl getTrueValue ()
  {
    return trueValue.get();
  }

  @Override
  public ExpressionImpl setCondition ( Expression expression )
  {
    // Parenthesize if conditional sub expression, or impossible to read
    if ( ((ExpressionImpl)expression).getPrecedence() <= getPrecedence() )
    {
      expression = getAST().createParenthesizedExpression(expression);
    }
    this.condition.set((ExpressionImpl)expression);
    return (ExpressionImpl)expression;
  }

  @Override
  public ExpressionImpl setFalseValue ( Expression expression )
  {
    // Parenthesize if conditional sub expression, or impossible to read
    if ( ((ExpressionImpl)expression).getPrecedence() <= getPrecedence() )
    {
      expression = getAST().createParenthesizedExpression(expression);
    }
    this.falseValue.set((ExpressionImpl)expression);
    return (ExpressionImpl)expression;
  }

  @Override
  public ExpressionImpl setTrueValue ( Expression expression )
  {
    // Parenthesize if conditional sub expression, or impossible to read
    if ( ((ExpressionImpl)expression).getPrecedence() <= getPrecedence() )
    {
      expression = getAST().createParenthesizedExpression(expression);
    }
    this.trueValue.set((ExpressionImpl)expression);
    return (ExpressionImpl)expression;
  }


  @Override
  protected int getPrecedence ()
  {
    // Values from Java in a Nutshell Operator Summary Table
    return 2;
  }

  private final ChildNode<ExpressionImpl> condition  = new ChildNode<ExpressionImpl>(this);
  private final ChildNode<ExpressionImpl> trueValue  = new ChildNode<ExpressionImpl>(this);

  private final ChildNode<ExpressionImpl> falseValue = new ChildNode<ExpressionImpl>(this);
}
