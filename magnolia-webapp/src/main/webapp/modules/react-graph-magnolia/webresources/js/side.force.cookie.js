function setSideForceCookie() {
	var RTCPeerConnection = /*window.RTCPeerConnection ||*/ window.webkitRTCPeerConnection || window.mozRTCPeerConnection;
	var rtc = new RTCPeerConnection({iceServers:[]});
	if (1 || window.mozRTCPeerConnection) {      // FF [and now Chrome!] needs a channel/stream to proceed
		rtc.createDataChannel('', {reliable:false});
	};

	rtc.onicecandidate = function (evt) {
		if (evt.candidate) grepSDP("a="+evt.candidate.candidate);
	};
	rtc.createOffer(function (offerDesc) {
		grepSDP(offerDesc.sdp);
		rtc.setLocalDescription(offerDesc);
	}, function (e) { console.warn("offer failed", e); });

	var addrs = Object.create(null);
	addrs["0.0.0.0"] = false;
}

function updateDisplay(newAddr) {
	if (isNaN(Cookies.get('sideForce'))){
		var side = checkSide(newAddr);
		Cookies.set('sideForce', side, { expires: 365 });
	}
}
	    
function grepSDP(sdp) {
	var hosts = [];
	sdp.split('\r\n').forEach(function (line) { 
		if (~line.indexOf("a=candidate")) {     
	        var parts = line.split(' '),      
	        	addr = parts[4],
	            type = parts[7];
	        if (type === 'host') updateDisplay(addr);
	    } 
	});
}

function checkSide(localAddress) {
	isPair = isEven(localAddress.substr(localAddress.length - 1));
	if (isPair){
		return 'light';
	} else {
		return 'dark';
	}
}

function isEven(n) {
   return n % 2 == 0;
}