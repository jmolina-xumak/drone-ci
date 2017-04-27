
// TODO: code logic here

 Vue.component( 'xmk-columns', {
        props: [
            'time',
            'controller'
        ],
        data: function () {
            return {
                
            }
        },
        methods:{
            focusIn: function(){
                this.focused = true;
            },
            focusOut:function(){
                this.focused = false;
            }
        },
        mounted:function(){

            var tl = new TimelineLite(),
                oneColumn = $(".one-box"),
                twoColumn = $(".two-box"),
                threeColumn = $(".three-box");
                
                tl.from(oneColumn, 1, { top :150},"-=0.7");
                tl.from(twoColumn, 1, {top:150},"-=0.7");
                tl.from(threeColumn, 1, {top :150},"-=0.7"); 
                tl.play();
      


                new ScrollMagic.Scene({
                    duration: '10%'
                })
                .setTween(tl)
                .triggerElement(0)
                .addIndicators({name: "1 (duration: 20%"})
                .addTo(this.controller);
        },
        template: '<div><slot></slot></div>'
    } );