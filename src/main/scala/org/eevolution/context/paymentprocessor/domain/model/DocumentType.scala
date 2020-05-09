/**
  * Copyright (C) 2003-2020, e-Evolution Consultants S.A. , http://www.e-evolution.com
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * Email: victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
  * Created by victor.perez@e-evolution.com , www.e-evolution.com
  **/
package org.eevolution.context.paymentprocessor.domain.model

import java.time.LocalDateTime

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{DateTime, Id, List, Table, TableDirect, YesNo}

/**
  * Document Type Entity
  *
  * @param documentTypeId Currency ID
  * @param tenantId       Tenant ID
  * @param organizationId Organization ID
  * @param isActive       Is Active
  * @param created        Created
  * @param createdBy      Created By
  * @param updated        Updated
  * @param updatedBy      Updated By
  * @param value          Value
  * @param name           Name
  * @param description    Description
  * @param uuid           UUID
  */
case class DocumentType(documentTypeId: Id,
                        tenantId: TableDirect,
                        organizationId: TableDirect,
                        isActive: YesNo = true,
                        created: DateTime = LocalDateTime.now,
                        createdBy: Table,
                        updated: DateTime = LocalDateTime.now,
                        updatedBy: Table,
                        value: String,
                        name: String,
                        description: Option[String],
                        documentBaseType: List,
                        uuid: String) extends DomainModel

  with ActiveEnabled
  with Identifiable
  with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = documentTypeId

  override val entityName: String = "C_DocType"
  override val identifier: String = "C_DocType_ID"
}

object DocumentType {
  //implicit lazy val jsonFormat = Jsonx.formatCaseClass[Currency]
  def create(documentTypeId: Id,
             tenantId: Id,
             organizationId: Id,
             isActive: YesNo,
             created: DateTime,
             createdBy: Table,
             updated: DateTime,
             updatedBy: Table,
             value: String,
             name: String,
             description: String,
             documentBaseType: List,
             uuid: String) = DocumentType(documentTypeId, tenantId, organizationId, isActive, created, createdBy, updated, updatedBy, value, name, Some(description), documentBaseType, uuid)
}