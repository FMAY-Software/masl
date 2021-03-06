//
// UK Crown Copyright (c) 2016. All Rights Reserved.
//
package org.xtuml.masl.metamodelImpl.type;

import org.xtuml.masl.metamodel.ASTNodeVisitor;
import org.xtuml.masl.metamodelImpl.common.Positioned;
import org.xtuml.masl.metamodelImpl.common.PragmaList;
import org.xtuml.masl.metamodelImpl.error.SemanticError;
import org.xtuml.masl.metamodelImpl.expression.Expression;
import org.xtuml.masl.utils.TextUtils;


public class StructureElement extends Positioned
    implements org.xtuml.masl.metamodel.type.StructureElement
{

  private final String     name;
  private final BasicType  type;
  private final Expression defaultValue;
  private final PragmaList pragmas;

  public static StructureElement create ( final String name,
                                          final BasicType type,
                                          final Expression defaultValue,
                                          final PragmaList pragmas )
  {
    if ( name == null || type == null || pragmas == null )
    {
      return null;
    }

    return new StructureElement(name, type, defaultValue, pragmas);
  }


  private StructureElement ( final String name, final BasicType type, Expression defaultValue, final PragmaList pragmas )
  {
    super(name);
    this.pragmas = pragmas;
    this.name = name;
    this.type = type;

    if ( defaultValue != null )
    {
      try
      {
        type.checkAssignable(defaultValue);
      }
      catch ( final SemanticError e )
      {
        e.report();
        defaultValue = null;
      }
    }
    this.defaultValue = defaultValue;
  }

  @Override
  public PragmaList getPragmas ()
  {
    return pragmas;
  }

  @Override
  public String getName ()
  {
    return name;
  }

  @Override
  public BasicType getType ()
  {
    return type;
  }

  @Override
  public Expression getDefault ()
  {
    return defaultValue;
  }

  @Override
  public String toString ()
  {
    return (comment == null ? "" : TextUtils.textBlock("", null, "// ", comment, "", true)) +
           name + "\t: "
           + type
           + (defaultValue == null ? "" : "\t:= " + defaultValue)
           + ";"
           +
           org.xtuml.masl.utils.TextUtils.formatList(pragmas.getPragmas(), "\t", "\t", "");
  }

  @Override
  public boolean equals ( final Object obj )
  {
    if ( this == obj )
    {
      return true;
    }
    if ( !(obj instanceof StructureElement) )
    {
      return false;
    }
    else
    {
      final StructureElement rhs = (StructureElement)obj;

      return super.equals(rhs)
             && name.equals(rhs.name)
             && type.equals(rhs.type)
             && (defaultValue == null ? rhs.defaultValue == null : defaultValue.equals(rhs.defaultValue));
    }
  }

  @Override
  public int hashCode ()
  {
    return ((super.hashCode() * 31 + name.hashCode()) * 31 + type.hashCode())
           * 31
           + (defaultValue == null ? 0 : defaultValue.hashCode());
  }

  public void setComment ( final String comment )
  {
    this.comment = comment;
  }


  @Override
  public String getComment ()
  {
    return comment;
  }

  private String comment;

  @Override
  public <R, P> R accept ( final ASTNodeVisitor<R, P> v, final P p ) throws Exception
  {
    return v.visitStructureElement(this, p);
  }
}
