/*
 * This file is part of RadPlanBio
 *
 * Copyright (C) 2013-2015 Tomas Skripcak
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

package de.dktk.dd.rpb.portal.web.util;

import java.util.Iterator;
import javax.faces.context.ExceptionHandler;
import javax.faces.event.ExceptionQueuedEvent;

import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;

/**
  * Exception handling is configured here, in web.xml (see error-page tag) and in faces-config.xml.
  *
  * @author initial source code generated by Celerio, a Jaxio product
  */
public class RpbExceptionHandler extends FullAjaxExceptionHandler {

    public RpbExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
  	public void handle() {
 	    Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();

        if (unhandledExceptionQueuedEvents.hasNext()) {
            Throwable exception = unhandledExceptionQueuedEvents.next().getContext().getException();
            MessageUtil.getInstance().error(exception);
        }
        super.handle();
    }

}