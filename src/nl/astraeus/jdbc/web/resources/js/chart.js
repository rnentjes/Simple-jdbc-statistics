/**
 *
 * User: rnentjes
 * Date: 4/2/11
 * Time: 4:49 PM
 *
 *  Requires jquery 1.5+
 */

Chart = function() {

    var options;
    var canvas;

    function executeDraw(data) {
        var canvasContext = canvas.getContext("2d");


    }

    function executeInit(c) {
        canvas = c;

        options = {};

        options.fillStyle = '#000000';
        options.strokeStyle = '#777777';
        options.lineWidth = 1;
    }

    /*
    data = { start: '', end: '', queries: [
     {
       query: #hash,
       thread: 1,
       start: '',
       duration: ''
      },
     {
     }
    ]
    */

    return {
        init: function(canvas) {
            executeInit(canvas);
        },

        draw: function(data) {
            executeDraw(data);
        }


    };

}();
