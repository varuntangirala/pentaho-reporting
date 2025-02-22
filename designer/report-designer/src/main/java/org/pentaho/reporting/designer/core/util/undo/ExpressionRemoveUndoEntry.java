/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.reporting.designer.core.util.undo;

import org.pentaho.reporting.designer.core.editor.ReportDocumentContext;
import org.pentaho.reporting.engine.classic.core.AbstractReportDefinition;
import org.pentaho.reporting.engine.classic.core.function.Expression;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public class ExpressionRemoveUndoEntry implements UndoEntry {
  private int position;
  private Expression expression;

  public ExpressionRemoveUndoEntry( final int position, final Expression expression ) {
    this.position = position;
    this.expression = expression;
  }

  public void undo( final ReportDocumentContext renderContext ) {
    final AbstractReportDefinition definition = renderContext.getReportDefinition();
    if ( definition.getExpressions().contains( expression ) == false ) {
      definition.getExpressions().add( position, expression );
      definition.notifyNodeChildAdded( expression );
    }
  }

  public void redo( final ReportDocumentContext renderContext ) {
    final AbstractReportDefinition definition = renderContext.getReportDefinition();
    definition.getExpressions().removeExpression( position );
    definition.notifyNodeChildRemoved( expression );
  }

  public UndoEntry merge( final UndoEntry newEntry ) {
    return null;
  }
}
