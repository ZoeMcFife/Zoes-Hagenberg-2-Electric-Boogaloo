@tool
class_name AnimatedTextBox

extends Node2D

@export_category("Text Settings")

@export
var text : String :
	set(value):
		text = value
		if text_label != null:
			text_label.text = value

@export_range(0, 2, 0.05, "suffix:s")
var time_per_letter : float = 0 

@export_range(0, 1, 0.01, "suffix:s")
var random_time_variation : float = 0

@export_range(0, 2, 0.1, "suffix:s")
var wait_time_before_text : float = 0

@export_range(0, 5, 0.1, "suffix:s")
var wait_time_before_end : float = 0

@export
var sound : AudioStream : 
	get:
		return sound
	set(value):
		sound = value
		if audio_player != null:
			audio_player.stream = sound

@export_range(0, 5, 0.05)
var min_pitch : float = 0 

@export_range(0, 5, 0.05)
var max_pitch : float = 0 

@export_category("Nodes")

@export
var sprite : AnimatedSprite2D

@export
var text_label : RichTextLabel

@export
var audio_player : AudioStreamPlayer

@export
var start_text_timer : Timer

@export_category("Tool")

@export_tool_button("Start Animation", "Callable")
var start_animation_callable : Callable = start_animation

signal bubble_started
signal text_started
signal text_changed
signal text_finished
signal bubble_finished

var lock : bool = false

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	sprite.play("start", 0)
	text_label.visible_characters = 0
	text_finished.connect(_on_text_finished)
	
	sprite.animation_finished.connect(_on_sprite_animation_finished)
	audio_player.stream = sound
	text_label.text = text
	
func start_animation() -> void:
	if lock:
		return
	
	bubble_started.emit()
	lock = true
	text_label.visible_characters = 0
	sprite.play("start")
	
	if wait_time_before_text != 0:
		await get_tree().create_timer(wait_time_before_text).timeout
		show_letters()
		
func _on_sprite_animation_finished() -> void:
	if sprite.animation == "start":
		sprite.play("loop")
		if wait_time_before_text == 0:
			show_letters()
		return
	
	if sprite.animation == "end":
		bubble_finished.emit()
		lock = false
		return
	
func show_letters() -> void:
	text_started.emit()
	
	for i : int in range(text.length()):
		text_label.visible_characters += 1
		
		text_changed.emit()
		
		audio_player.pitch_scale = randf_range(min_pitch, max_pitch)
		audio_player.play()
		
		await get_tree().create_timer(time_per_letter * randf_range(1, 1 + random_time_variation)).timeout
	
	text_finished.emit()

func hide_letters() -> void:
	for i : int in range(text.length()):
		text_label.visible_characters -= 1
		
		await get_tree().create_timer(0.01).timeout

func _on_text_finished() -> void:
	await get_tree().create_timer(wait_time_before_end).timeout
	
	hide_letters()
	sprite.play("end")
