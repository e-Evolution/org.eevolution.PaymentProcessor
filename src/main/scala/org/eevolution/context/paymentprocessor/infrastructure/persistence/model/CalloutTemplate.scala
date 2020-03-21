package org.eevolution.context.paymentprocessor.infrastructure.persistence.model

import java.util.Properties

import org.adempiere.model.GridTabWrapper
import org.compiere.model._

/**
 * Created by eEvolution author Victor Perez <victor.perez@e-evolution.com> on 19/08/15.
 */
class CalloutTemplate extends CalloutEngine{

    def change(ctx: Properties, windowNo: Int, gridTab: GridTab, gridField: GridField, value: AnyRef): String = {

      val model: I_C_BPartner = GridTabWrapper.create(gridTab, classOf[I_C_BPartner])
      if (isCalloutActive) return ""
      return ""
    }
}
