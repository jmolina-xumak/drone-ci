// TODO: code logic here

Vue.component( 'xmk-input', {
    props: [
        'name',
        'label',
        'status',
        'validatorMessage',
        'placeholder'
    ],
    data: function () {
        return {
            focused : false,
            input: ''
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
    computed:{
        hasStatus : function (){
            return (this.input.length  > 0 || this.focused) ? 'MainInput--focused ' + this.status : '' ;
        }
    },
    template:'#xmk-input'
} );

Vue.component('xmk-select', {
    props: ['value', 'id', 'data', 'label', 'placeholder'],
    data: function () {
    return {
        selected: null,
        isOpen: false,
        isHidding: false,
        options: []
    }
    },
    created: function(){
    this.options = this.data;
    },
    methods: {
    hidden: function(){
        this.isOpen = false;
    },
    toggle: function() {
        if ( !this.disabled ) {
        this.isOpen = !this.isOpen;
        }
    },
    update: function( item ) {
        this.selected = item;
        this.options = this.options.map(function(it){
        it.selected = false;
        return it;
        });
        this.$emit('input', this.selected.value);
    }
    },
    computed: {
    hasStatus : function (){
        return (this.selected || this.isOpen) ;
    }
    },
    template: '#xmk-select'
});

new Vue({}).$mount('#xmkApp');

