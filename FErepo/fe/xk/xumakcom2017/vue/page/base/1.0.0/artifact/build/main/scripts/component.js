'use strict';

// TODO: code logic here
(function () {
    Vue.component('xmk-input', {
        props: ['name', 'label', 'status', 'validatorMessage', 'placeholder'],
        data: function data() {
            return {
                focused: false,
                input: ''
            };
        },
        methods: {
            focusIn: function focusIn() {
                this.focused = true;
            },
            focusOut: function focusOut() {
                this.focused = false;
            }
        },
        computed: {
            hasStatus: function hasStatus() {
                return this.input.length > 0 || this.focused ? 'MainInput--focused ' + this.status : '';
            }
        },
        template: '#xmk-input'
    });

    Vue.component('xmk-select', {
        props: ['value', 'id', 'data', 'label', 'placeholder'],
        data: function data() {
            return {
                selected: null,
                isOpen: false,
                isHidding: false,
                options: []
            };
        },
        created: function created() {
            this.options = this.data;
        },
        methods: {
            hidden: function hidden() {
                this.isOpen = false;
            },
            toggle: function toggle() {
                if (!this.disabled) {
                    this.isOpen = !this.isOpen;
                }
            },
            update: function update(item) {
                this.selected = item;
                this.options = this.options.map(function (it) {
                    it.selected = false;
                    return it;
                });
                this.$emit('input', this.selected.value);
            }
        },
        computed: {
            hasStatus: function hasStatus() {
                return this.selected || this.isOpen;
            }
        },
        template: '#xmk-select'
    });

    new Vue({}).$mount('#xmkApp');
})();