@tool
class_name Fox

extends Node2D

@export_category("Foxes")

@export
var fox_container : Node2D

@export
var sprite : Sprite2D

@export
var flip_sprite : bool = false : 
	set(value):
		flip_sprite = value
		sprite.flip_h = value

@export_enum("Fox1","Fox2","Fox3","Fox4","Fox5","Fox6","Fox7","Fox8","Fox9","Fox10")
var fox_img : int = 0:
	set(value):
		fox_img = value
		_update_sprite()

@export_category("Animation")

@export
var animation_tree : AnimationTree

@export_category("Text Settings")

@export
var text_box : AnimatedTextBox

@export
var text : String = "":
	set(value):
		text = value
		if text_box:
			text_box.text = value

@export_range(0, 2, 0.05, "suffix:s")
var time_per_letter : float = 0 :
	set(value):
		time_per_letter = value
		if text_box:
			text_box.time_per_letter = value


@export_range(0, 1, 0.01, "suffix:s")
var random_time_variation : float = 0 :
	set(value):
		random_time_variation = value
		if text_box:
			text_box.random_time_variation = value

@export_range(0, 5, 0.1, "suffix:s")
var wait_time_before_end : float = 0 :
	set(value):
		wait_time_before_end = value
		if text_box:
			text_box.wait_time_before_end = value

@export
var sound : AudioStream :
	set(value):
		sound = value
		if text_box:
			text_box.sound = value

@export_range(0, 5, 0.05)
var min_pitch : float = 0 :
	set(value):
		min_pitch = value
		if text_box:
			text_box.min_pitch = value

@export_range(0, 5, 0.05)
var max_pitch : float = 0 :
	set(value):
		max_pitch = value
		if text_box:
			text_box.max_pitch = value

@export_category("Tool")

@export_tool_button("Start Animation", "Callable")
var say_callable : Callable = say_text

func _ready():
	animation_tree.set("parameters/Blend/blend_amount", 0)
	
	_update_sprite()
	
	if text_box:
		text_box.text = text
		text_box.time_per_letter = time_per_letter
		text_box.random_time_variation = random_time_variation
		text_box.wait_time_before_end = wait_time_before_end
		text_box.sound = sound
		text_box.min_pitch = min_pitch
		text_box.max_pitch = max_pitch
		
		text_box.text_started.connect(_on_text_box_text_started)
		text_box.text_finished.connect(_on_text_box_text_finished)
	
	say_text()

func say_text() -> void:
	say(text)

func say(to_say : String) -> void:
	text = to_say
	
	text_box.start_animation()

func _on_text_box_text_started() -> void:
	animation_tree.set("parameters/Blend/blend_amount", 0.5)

func _on_text_box_text_finished() -> void:
	animation_tree.set("parameters/Blend/blend_amount", 0)

func _update_sprite():
	sprite.texture = load("res://fox/sprites/fox%d.png" % (fox_img + 1))
