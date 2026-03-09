extends Node3D

func _ready() -> void:
	$AnimationPlayer.play("start")

func _input(event):
	if Input.is_action_just_pressed("fox_event_0"):
		fox_event_0()
	elif Input.is_action_just_pressed("fox_event_1"):
		fox_event_1()
	elif Input.is_action_just_pressed("fox_event_2"):
		fox_event_2()
	elif Input.is_action_just_pressed("fox_event_3"):
		fox_event_3()
	elif Input.is_action_just_pressed("fox_event_4"):
		fox_event_4()
	elif Input.is_action_just_pressed("fox_event_5"):
		fox_event_5()
	elif Input.is_action_just_pressed("fox_event_6"):
		fox_event_6()
	elif Input.is_action_just_pressed("fox_event_7"):
		fox_event_7()
	elif Input.is_action_just_pressed("fox_event_8"):
		fox_event_8()
	elif Input.is_action_just_pressed("fox_event_9"):
		fox_event_9()

func fox_event_0() -> void:
	var p : Player = $Player
	var c : CarControl = $CarControl
	
	if p.controlling_car:
		p.toggle_car()
	
	p.global_position = $FurryTribe.global_position
	c.global_position = $FurryTribeCar.global_position
	
	
func fox_event_1() -> void:
	var fox1 : Fox = $CanvasLayer/Fox1
	var ex : Explosion = $CanvasLayer/FoxExplosion
	fox1.show()

	fox1.say_text()
	
	await fox1.fox_finished
	
	ex.global_position = fox1.global_position
	ex.start_explosion()
	
	await get_tree().create_timer(0.3).timeout
	
	fox1.hide()
	
func fox_event_2() -> void:
	var fox2 : Fox = $CanvasLayer/Fox2
	var ex : Explosion = $CanvasLayer/FoxExplosion
	fox2.show()

	fox2.say_text()
	
	await fox2.fox_finished
	
	ex.global_position = fox2.global_position
	ex.start_explosion()
	
	await get_tree().create_timer(0.3).timeout
	
	fox2.hide()
	
func fox_event_3() -> void:
	var fox3 : Fox = $CanvasLayer/Fox3
	var ex : Explosion = $CanvasLayer/FoxExplosion
	fox3.show()

	fox3.say_text()
	
	await fox3.fox_finished
	
	fox3.flip_sprite = true
	
	ex.global_position = fox3.global_position
	ex.start_explosion()
	
	await get_tree().create_timer(0.3).timeout
	
	fox3.hide()
	
func fox_event_4() -> void:
	var fox4 : Fox = $CanvasLayer/Fox4
	var ex : Explosion = $CanvasLayer/FoxExplosion
	fox4.show()

	fox4.say_text()
	
	await fox4.fox_finished
	
	await get_tree().create_timer(0.3).timeout
	
	fox4.fox_img = 9
	
	await get_tree().create_timer(0.3).timeout

	
	ex.global_position = fox4.global_position
	ex.start_explosion()
	
	await get_tree().create_timer(0.3).timeout
	
	fox4.hide()
	fox4.fox_img = 8
	
func fox_event_5() -> void:
	pass

func fox_event_6() -> void:
	pass

func fox_event_7() -> void:
	pass

func fox_event_8() -> void:
	pass

func fox_event_9() -> void:
	pass
