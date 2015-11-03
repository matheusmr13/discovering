 $(document).ready(function() {
//    var heatmap = [];
//    var w = parseInt(window.innerWidth/10);
//    var h = parseInt(window.innerHeight/10);
//    var actualX, actualY;
//    for (var i=0;i<w;i++) {
//        var a = [];
//        for (var j=0;j<h;j++) {
//            a.push(0);
//        }
//        heatmap.push(a);
//    }
//
//    var ctx = document.getElementById('canvas').getContext('2d');
//    var redraw = function() {
//        ctx.clearRect(0, 0, 1500, 1500);
//        for (var i=0;i<w;i++) {
//            for (var j=0;j<h;j++) {
//                if (heatmap[i][j]) {
//                    var x = i*2;
//                    var y = j*2;
//                    var grd = ctx.createRadialGradient(x+heatmap[i][j]/2,y+heatmap[i][j]/2,heatmap[i][j]/4,x+heatmap[i][j]/2,y+heatmap[i][j]/2,heatmap[i][j]/2);
//                    grd.addColorStop(0,"rgba(255,0,0,1)");
//                    grd.addColorStop(1,"rgba(255,0,0,0)");
//
//                    // Fill with gradient
//                    ctx.fillStyle = grd;
//                    ctx.fillRect(x,y,heatmap[i][j]*2,heatmap[i][j]*2);
//                }
//            }
//        }
//    };
//    setInterval(function() {
//        heatmap[actualX][actualY]++;
//        redraw();
//    }, 100);
    var getInfosBrowser = function() {
        var nVer = navigator.appVersion;
        var nAgt = navigator.userAgent;
        var browserName  = navigator.appName;
        var fullVersion  = ''+parseFloat(navigator.appVersion); 
        var majorVersion = parseInt(navigator.appVersion,10);
        var nameOffset,verOffset,ix;

        // In Opera, the true version is after "Opera" or after "Version"
        if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
           browserName = "Opera";
           fullVersion = nAgt.substring(verOffset+6);
           if ((verOffset=nAgt.indexOf("Version"))!=-1) 
             fullVersion = nAgt.substring(verOffset+8);
        }
        // In MSIE, the true version is after "MSIE" in userAgent
        else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
           browserName = "Microsoft Internet Explorer";
           fullVersion = nAgt.substring(verOffset+5);
        }
        // In Chrome, the true version is after "Chrome" 
        else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
           browserName = "Chrome";
           fullVersion = nAgt.substring(verOffset+7);
        }
        // In Safari, the true version is after "Safari" or after "Version" 
        else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
           browserName = "Safari";
           fullVersion = nAgt.substring(verOffset+7);
           if ((verOffset=nAgt.indexOf("Version"))!=-1) 
             fullVersion = nAgt.substring(verOffset+8);
        }
        // In Firefox, the true version is after "Firefox" 
        else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
            browserName = "Firefox";
            fullVersion = nAgt.substring(verOffset+8);
        }
        // In most other browsers, "name/version" is at the end of userAgent 
        else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < (verOffset=nAgt.lastIndexOf('/')) ) {
            browserName = nAgt.substring(nameOffset,verOffset);
            fullVersion = nAgt.substring(verOffset+1);
            if (browserName.toLowerCase()==browserName.toUpperCase()) {
               browserName = navigator.appName;
            }
        }
        // trim the fullVersion string at semicolon/space if present
        if ((ix=fullVersion.indexOf(";"))!=-1)
            fullVersion=fullVersion.substring(0,ix);
        if ((ix=fullVersion.indexOf(" "))!=-1)
            fullVersion=fullVersion.substring(0,ix);

        majorVersion = parseInt(''+fullVersion,10);
        if (isNaN(majorVersion)) {
            fullVersion  = ''+parseFloat(navigator.appVersion); 
            majorVersion = parseInt(navigator.appVersion,10);
        }

        return {browser: browserName, version : fullVersion, majorVersion: majorVersion, navigatorAppName:navigator.appName, navigatorUserAgent : navigator.userAgent};
    };

    var infos = getInfosBrowser();
     infos.startDate = yawpUtils.dateToYawpDate(new Date());
    var basicEventPos = function (e, t) {
        return {x:e.pageX,
                y:e.pageY,
                timeSince: e.timeStamp,
                actionType:t}
    }
    var initialTimestamp = new Date().getTime();
    var criarDiv = function(cor) {
        return '<div style="width:10px;height:10px;background-color:'+cor+';position:absolute;"></div>';
    };

    var divClick = criarDiv('red');
    var divDrag = criarDiv('green');
    var divMove = criarDiv('blue');
    var pos = [];
    var holding = false;
    $('body').mousemove(function(e) {
//        actualX = parseInt(e.pageX/10);
//        actualY = parseInt(e.pageY/10);
        if (holding) {
            pos.push(basicEventPos(e,'MOUSE_DRAG'));
        } else {
            pos.push(basicEventPos(e,'MOUSE_MOVE'));
        }
    });
    $('body').mousedown(function(e) {
        holding = true;
        pos.push(basicEventPos(e,'MOUSE_CLICK'));
    });
    $('body').mouseup(function(e) {
        holding = false;
    });
    $('input').keydown(function(e) {
        pos.push({
            t:'i',
            i:$(this),
            c:String.fromCharCode(e.keyCode)
        });
    });

    $('button').click(function() {
        infos.actions = pos;
        yawp('/recordings').create(infos);
    });
});