package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.model.TinDetails;


@Service
public class ScrapeDataService {

	private Logger LOGGER = LoggerFactory.getLogger(ScrapeDataService.class);

	/**
	 * @author RV
	 * @param url
	 * @param postParams
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Collection fetchScrapedData(String url, String tinNo) throws Exception {
		LOGGER.info(">> Fetching TIN Details <<");
		Collection<TinDetails> tinDetailsList = null;

		Connection.Response res = Jsoup.connect(url).timeout(0)
				.header("Content-Type","application/x-www-form-urlencoded")
				.data("tin", tinNo)
				.data("pan", "")
				.data("rc_no", "")
				.data("fptecno", "")
				.data("bptecno", "")
				.data("DEALERNAME", "")
				.data("Submit", "SEARCH")
				.method(Method.POST)
				.execute();

		Document doc = res.parse();

		if(null != doc){
			LOGGER.info("++ Fetched TIN Details Successfully ++");
			tinDetailsList = new ArrayList<>();
			Elements tds = null;
			Elements form = doc.select("[name=f1]");
			
			if(form != null){
				if(null != form.select("table").last()){
				
					tds = form.select("table").last().select("tr").last().select("td");

					if(null != tds){
						tinDetailsList.add(
								new TinDetails( 
										tds.get(1).ownText(), 
										tds.get(2).select("a").text(), 
										tds.get(3).ownText(),
										tds.get(4).ownText()));
					}
				}
				
			}






		}
		return tinDetailsList;
	}

}