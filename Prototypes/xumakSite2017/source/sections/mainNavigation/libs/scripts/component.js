
// TODO: code logic here
Vue.component('mainNavigation', function mainNavCreator(resolve, reject) {
    $.ajax('/assets/templates/main-navigation.html').done(function(template){
        resolve({
            props:['structure', 'dataImagePath', 'dataImageAltText', 'dataRootPath'],
            template: template,
            data: function(){
                return {
                    menu: {}
                }
            },
            created: function(){
                var vm = this;
                this.menu = this.structure;
                this.menu.pages = this.menu.pages.map(function(el) {
                    vm.$set(el,'isExpanded', false);
                    return el;
                });
                vm.$set(this.menu, 'isVisible', false);
                vm.$set(this.menu, 'isSlided', false);
            },
            methods: {
                toggleMenu: function() {
                    this.menu.isVisible = !this.menu.isVisible;
                    this.menu.isSlided = !this.menu.isSlided;
                    this.mapReset();
                    $('.MainNavigation-subList').attr('style','')
                    var self = this;
                    if ( this.menu.isVisible ) {
                        $('<div class="MainNavigation-overlay"/>').appendTo('body').animate({
                            opacity: .8
                        }, 500).on('click', function(){
                            self.removeOverlay();
                        })
                      
                    } else {
                        $('.MainNavigation-overlay').animate({
                            opacity: 0
                        }, 500, function() {
                            $(this).remove();
                        });
                    }
                },
                mouseEnter: function(item, el){
                    //If the element is expanded close himself
                    if(item.isExpanded) {
                        item.isExpanded = !item.isExpanded;
                        $('.MainNavigation-subList.isExpanded').css({
                            'animation': 'out .5s ease forwards'
                        });
                        
                        return false
                    }
                    var self = this;

                    this.mapReset();

                    if(item.hasChildren ) {
                        $('.MainNavigation-arrow.isActive').removeClass('isActive');
                        item.isExpanded = true;
                        this.menu.isVisible = true;

                        var listItem = $(el.target).parents('.MainNavigation-item');
                        //if some of the link in the main nav has the active state then remove the stata
                        $('.MainNavigation-link').removeClass('isActive');

                        //if some submenu is visible, collapse it
                        $('.MainNavigation-subList.isExpanded').css({
                            'animation': 'out .5s ease forwards'
                        });

                        //Set the active class to clicked link
                        $(el.target).parents('.MainNavigation-item').find('.MainNavigation-link').addClass('isActive');

                        //Show the main menu background if dont have it yet
                        this.menu.isSlided = true;

                        //Show the submenu of the item clicked
                        $(listItem[0]).find('.MainNavigation-subList').addClass('isExpanded').css({
                            'animation':'in .5s .3s ease forwards'
                        });
                        
                        //show the overlay if dont exist
                        if($('.MainNavigation-overlay').length > 0){
                                return false;
                        } else {
                            $('<div class="MainNavigation-overlay"/>').appendTo('body').animate({
                                opacity: .8
                            }, 500).on('click', function(){
                                self.removeOverlay();
                            });
                        }
                    } else {
                        return false;
                    }
                },
                removeOverlay: function() {

                    this.mapReset();
                    //collapse sub menu and remove the main menu background
                    $('.MainNavigation-arrow.isActive').removeClass('isActive');
                    $('.MainNavigation-component.isActive').removeClass('isActive');
                    $('.MainNavigation-subList.isExpanded').css({
                        'animation':'out .5s ease forwards'
                    }).removeClass('isExpanded');

                    //Delay to wait until sub menu collapse complety
                    setTimeout(function(){
                        this.menu.isSlided = false;
                        this.menu.isVisible = false;

                    }.bind(this), 500);

                    $('.MainNavigation-link').removeClass('isActive');
                    
                    //remove the overlay
                    $('.MainNavigation-overlay').remove();
                },
                mapReset: function() {
                    this.menu.pages = this.menu.pages.map(function(el) {
                        el.isExpanded = false;
                        return el;
                    });
                }
            }
        })
    });
});