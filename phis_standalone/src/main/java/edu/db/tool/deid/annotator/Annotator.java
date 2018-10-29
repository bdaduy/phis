/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import edu.db.tool.deid.document.IDocument;

/**
 *
 * @author dbui
 */
public class Annotator {

    public static void annotate(IDocument doc) {
        doc.partitionSection();

        doc.addPHI(Annotator_CRF_I2b22014_1304_LDS.annotate(doc));
        doc.addPHI(Annotator_CRF_UAB_305_LDS.annotate(doc));
        doc.addPHI(Annotator_CRF_I2b22014_1304_AgeDateZip.annotate(doc));
        doc.addPHI(Annotator_Patient_StructuredDB.annotate(doc));

        doc.addPHI(Annotator_Common_Human_Names.annotate(doc));
        doc.addPHI(Annotator_Regex.annotate(doc));
        doc.addPHI(Annotator_Regex_AgeDateZip.annotate(doc));

        doc.addPHI(Annotator_Dict_Manual_StandaloneOnly.annotate(doc));
        doc.addPHI(Annotator_Dict_Country.annotate(doc));

    }

}
