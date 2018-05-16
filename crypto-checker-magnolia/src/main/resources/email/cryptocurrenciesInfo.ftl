

<html>
  
  <h1 style="text-align: center; color: #689600; font-family: 'Roboto', sans-serif;"> Información sobre criptomonedas </h1>
  
  <h1 style="text-align: center; color: #689600; font-family: 'Roboto', sans-serif;"> Los datos sobre las monedas dadas a fecha de ${emailData.fecha?string["dd-MM-yyyy, HH:mm"]} son los siguientes: </h1>
  
  <br/>
  
  
  <section style="background-color: white; font-family: 'Roboto', sans-serif;">
  <div class="tbl-header" style="background-color: #C3D599;">
    <table cellpadding="0" cellspacing="0" border="0" style="width: 100%; table-layout: fixed;">
      <tbody>
        <tr>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Nombre</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Valor (EUR)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Valor (USD)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Valor (JPY)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">High day (USD)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Low day (USD)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">High day (EUR)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Low day (EUR)</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Total Volume 24H</th>
          <th style="padding: 20px 15px; text-align: left;  font-weight: 500;font-size: 12px; color: #fff; text-transform: uppercase;">Average</th>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="tbl-content" style="height:300px;margin-top: 0px;border: 1px solid white;">
     <table cellpadding="0" cellspacing="0" border="0" style="width: 100%; table-layout: fixed;">
      <tbody>
        <#list emailData.nodes as nodeName, nodeValue>
        	 <tr>
        	 <td style="padding: 15px; text-align: left; vertical-align:middle; font-weight: 300; font-size: 12px; color: black; border-bottom: solid 1px rgba(255,255,255,0.1); border: 1px solid #C3D599;">${nodeName}</td>
  			 <#list nodeValue as propName, propValue>
  			 	  <td style="padding: 15px; text-align: left; vertical-align:middle; font-weight: 300; font-size: 12px; color: black; border-bottom: solid 1px rgba(255,255,255,0.1); border: 1px solid #C3D599;">${propValue}</td>
  			 </#list>
  			</tr>
  		</#list>
      </tbody>
    </table>
  </div>
</section>
</html>