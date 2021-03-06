/*
 * This file is part of RadPlanBio
 *
 * Copyright (C) 2013-2016 Tomas Skripcak
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.dktk.dd.rpb.core.domain.edc;

import com.google.common.base.Objects;
import de.dktk.dd.rpb.core.domain.Identifiable;
import de.dktk.dd.rpb.core.domain.IdentifiableHashBuilder;
import org.apache.log4j.Logger;

import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * EventData domain entity (based on CDISC ODM StudyEventData)
 *
 * @author tomas@skripcak.net
 * @since 24 Feb 2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StudyEventData")
public class EventData implements Identifiable<Integer>, Serializable {

    //region Finals

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(EventData.class);

    //endregion

    //region Members

    @XmlTransient
    private Integer id;

    @XmlAttribute(name="StudyEventOID")
    private String studyEventOid;

    @XmlAttribute(name="EventName", namespace="http://www.openclinica.org/ns/odm_ext_v130/v3.1")
    private String eventName;

    @XmlAttribute(name="StartDate", namespace="http://www.openclinica.org/ns/odm_ext_v130/v3.1")
    private String startDate;

    @XmlAttribute(name="Status", namespace="http://www.openclinica.org/ns/odm_ext_v130/v3.1")
    private String status;

    @XmlAttribute(name="StudyEventRepeatKey")
    private String studyEventRepeatKey;

    @XmlTransient
    private EventDefinition eventDefinition;

    @XmlElement(name="FormData")
    private List<FormData> formDataList;

    @XmlTransient
    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder(); // Object hash

    //endregion

    //region Constructors

    public EventData() {
        // NOOP
    }

    public EventData(String studyEventOid) {
        this();

        this.studyEventOid = studyEventOid;
    }

    public EventData(String studyEventOid, String studyEventRepeatKey) {
        this(studyEventOid);

        this.studyEventRepeatKey = studyEventRepeatKey;
    }

    //endregion

    //region Properties

    //region Id

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    @Transient
    public boolean isIdSet() {
        return this.id != null;
    }

    //endregion

    //region StudyEventOID

    public String getStudyEventOid() {
        return this.studyEventOid;
    }

    public void setStudyEventOid(String value) {
        this.studyEventOid = value;
    }

    //endregion

    //region EventName

    public String getEventName() {
        if (this.eventName != null) {
            return this.eventName;
        }
        else if (this.eventDefinition != null) {
            return this.eventDefinition.getName();
        }

        return null;
    }

    public void setEventName(String value) {
        this.eventName = value;
    }

    //endregion

    //region StartDate

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String value) {
        this.startDate = value;
    }

    //endregion

    //region Status

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    //endregion

    //region StudyEventRepeatKey

    public String getStudyEventRepeatKey() {
        return this.studyEventRepeatKey;
    }

    public void setStudyEventRepeatKey(String value) {
        this.studyEventRepeatKey = value;
    }

    //endregion

    //region EventDefinition

    public EventDefinition getEventDefinition() {
        return this.eventDefinition;
    }

    public void setEventDefinition(EventDefinition eventDefinition) {
        this.eventDefinition = eventDefinition;
    }

    //endregion

    //region FormData

    public List<FormData> getFormDataList() {
        return this.formDataList;
    }

    public void setFormDataList(List<FormData> list) {
        this.formDataList = list;
    }

    /**
     * Helper method to add the passed {@link FormData} to the formDataList list
     */
    public boolean addFormData(FormData form) {
        if (!this.containsFormData(form)) {
            if (this.formDataList == null) {
                this.formDataList = new ArrayList<>();
            }
            return this.formDataList.add(form);
        }

        return false;
    }

    /**
     * Helper method to remove the passed {@link FormData} from the formDataList list
     */
    public boolean removeFormData(FormData fromData) {
        return this.containsFormData(fromData) && this.formDataList.remove(fromData);
    }

    /**
     * Helper method to determine if the passed {@link FormData} is present in the formDataList list
     */
    public boolean containsFormData(FormData fromData) {
        return this.formDataList != null && this.formDataList.contains(fromData);
    }

    /**
     * Helper method to determine whether passed form OID is present in the formDataList list
     * @param formOid formOid to lookup for
     * @return true if the formData with specified form OID exists within event
     */
    public boolean containsFormData(String formOid) {
        if (this.formDataList != null) {
            for (FormData formData : this.formDataList) {
                if (formData.getFormOid().equals(formOid)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Helper method to get formData according to form OID
     * @param formOid formOid to lookup for
     * @return formData if exists
     */
    public FormData findFormData(String formOid) {
        if (this.formDataList != null) {
            for (FormData formData : this.formDataList) {
                if (formData.getFormOid().equals(formOid)) {
                    return formData;
                }
            }
        }

        return null;
    }

    //endregion

    //endregion

    //region Overrides

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof EventData && hashCode() == other.hashCode());
    }

    /**
     * Generate entity hash code
     * @return hash
     */
    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this, this.studyEventOid);
    }

    /**
     * Construct a readable string representation for this RtStructType instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("studyEventOid", this.studyEventOid)
                .add("studyEventRepeatKey", this.studyEventRepeatKey)
                .toString();
    }

    //endregion

    //region Methods

    //region Metadata

    public void linkOdmDefinitions(Odm odm) {
        if (odm != null) {

            // EventDefinition linking
            EventDefinition eventDefinition = odm.findUniqueEventDefinitionOrNone(this.studyEventOid);
            if (eventDefinition != null) {
                this.setEventDefinition(eventDefinition);
            }
            // EventDefinition does not exists in metadata - event removed
            else {
                this.setStatus(EnumEventDataStatus.INVALID.toString());
            }

            // Link next level in hierarchy (CRF)
            if (this.formDataList != null) {
                for (FormData formData : this.formDataList) {
                    formData.linkOdmDefinitions(odm);
                }
            }
        }
    }

    //endregion

    //endregion

}