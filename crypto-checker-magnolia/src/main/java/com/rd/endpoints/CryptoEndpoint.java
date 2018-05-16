package com.rd.endpoints;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.constants.ConstantsCommon;
import com.rd.service.ICryptoService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/crypto-rest")
@Path("/crypto-rest")
public class CryptoEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private ICryptoService cryptoService;

	@Inject
	public CryptoEndpoint(final D endpointDefinition) {
		super(endpointDefinition);
	}

	/**
	 * @param email
	 * @return 
	 */
	@GET
	@Path("/addEmail")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@ApiOperation(value = "Crypto currencies endpoint")
	public Response addEmail(@QueryParam("email") String email) {
		
		JSONObject jsonRes = new JSONObject();
		log.info("REST CRYPTOCURRENCIES APP - Service name: addEmail");
		Date before = new Date();

		try {

			if (! this.cryptoService.addEmail(email)) {
				
				jsonRes.put(ConstantsCommon.RESPONSE_KEY, ConstantsCommon.ERROR_RESULT + " - No agent created");
				jsonRes.put(ConstantsCommon.RESPONSE_CODE_KEY, HttpStatus.SC_INTERNAL_SERVER_ERROR);
				jsonRes.put(ConstantsCommon.DATA_KEY, "");
				return this.cryptoService.compileErrorResponseBuilder(jsonRes, Response.Status.INTERNAL_SERVER_ERROR).build();

			} else {
				
				jsonRes.put(ConstantsCommon.RESPONSE_KEY, ConstantsCommon.SUCCESS_RESULT);
				jsonRes.put(ConstantsCommon.RESPONSE_CODE_KEY, HttpStatus.SC_OK);
				jsonRes.put(ConstantsCommon.DATA_KEY, "");
			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			jsonRes.put(ConstantsCommon.RESPONSE_KEY, ConstantsCommon.ERROR_RESULT + "-" + e.toString());
			jsonRes.put(ConstantsCommon.RESPONSE_CODE_KEY, HttpStatus.SC_INTERNAL_SERVER_ERROR);
			jsonRes.put(ConstantsCommon.DATA_KEY, "");
			return this.cryptoService.compileErrorResponseBuilder(jsonRes, Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		String res = new String(jsonRes.toString().getBytes(), StandardCharsets.UTF_8);

		ResponseBuilder rp = generateResponseBuilder(res);

		Date after = new Date();
		log.info("REST APP DESTINATION SERVICES - Elapsed time of newAgent" + ": "
				+ (after.getTime() - before.getTime()) + " milliseconds");

		return rp.build();
	}

	private ResponseBuilder generateResponseBuilder(String res) {

		ResponseBuilder responseBuilder = Response.ok();

		responseBuilder.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0").entity(res);

		return responseBuilder;
	}

}
