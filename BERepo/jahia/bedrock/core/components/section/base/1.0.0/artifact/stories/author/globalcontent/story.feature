Feature: Basic Managment of Global Content
	So that I can manage global content related to all instances of this Component within a Page type
	As a Global Author
	I want to be able to add, update, and delete content using a Global dialog

	Scenario: All content is correct
		Given that a user is a Global Author
			and all content that is necessary is entered
			and that all entered content satisfies the validation rules
		When an Author clicks Save on the Dialog
		Then the content entered is saved on the Global Resource
			and the Dialog closes
			and the Component is redrawn to show the new content

	Scenario: Not all content is correct
		Given that a user is a Global Author
			and all content that is necessary is entered
			and some of the entered content does not satisfy the validation rules
		When an Author clicks Save on the Dialog
		Then the content that violates the rules is highlighted
			and a message describing the problem is displayed
			and the Dialog stays open
