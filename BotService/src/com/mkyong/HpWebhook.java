package com.mkyong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dbCon.DbOps;
import com.failData.FailData;
import com.failData.Jobs_status;
import com.hawkeye.utils.BaseUtils;
import com.loadPojo.DMRpojo;
import com.loadPojo.LoadJobPojo;
import com.shipStatusPojo.TraingCenterPojo;

@Path("/AOL")
public class HpWebhook {
    
	String pincode = null;	

	@POST
	@Path("/aolinfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipmentStatusJsonResponse(JSONObject request) throws JSONException, InterruptedException {

		System.out.println("i am prem");
		
		/*if(request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("pincode"))
		{
			
		pincode = request.getJSONObject("result").getJSONObject("parameters").getString("pincode");
		System.out.println(pincode);
		}
		
		*/
		if(request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("typeofjob"))
		{
		
		String pincode = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters").getString("pincode");
		System.out.println(pincode);
		DbOps dbQuery = new DbOps();
		//System.out.println(request.getResult().getParameters().getShipmentid());
		//pincode = "431206";
		List<TraingCenterPojo> listTrainings  = dbQuery.getCenters(pincode);
		
				
		if(listTrainings!=null) {
		
		JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
		List<Messages> listOfMessages = new ArrayList<Messages>();
		
		String speech = "Below are the list of training centers :";
		Messages message = new Messages();
		message.setSpeech(speech);
		message.setType(0);
		listOfMessages.add(message);
		
		for (TraingCenterPojo traingCenterPojo : listTrainings) {
			Messages messageCenter = new Messages();
			messageCenter.setSpeech(traingCenterPojo.getCenters());
			messageCenter.setType(0);
			listOfMessages.add(messageCenter);
		}
		
		// response.setSpeech(speech);
		response.setMessages(listOfMessages);
		response.setSource("agent");
		
		System.out.println(response.getMessages());
		return Response.status(201).entity(response).build();

		}
		else {		
			JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
			Messages message = new Messages();
			List<Messages> listOfMessages = new ArrayList<Messages>();
			
			String speech = "Sorry! \n No Training centers are available.";
			message.setSpeech(speech);
			message.setType(0);
			listOfMessages.add(message);
			
			response.setMessages(listOfMessages);
			response.setSource("agent");
			
			return Response.status(201).entity(response).build();
			
				
		}
		
		}

return null;
		
	}

	
		
	}



