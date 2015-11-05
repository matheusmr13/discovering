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


//        actualX = parseInt(e.pageX/10);
//        actualY = parseInt(e.pageY/10);