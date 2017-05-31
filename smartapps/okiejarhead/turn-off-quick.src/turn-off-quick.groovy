/**
 *  Turn Off Quick
 *
 *  Copyright 2016 Jeffery Johnson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Turn Off Quick",
    namespace: "okiejarhead",
    author: "Jeffery Johnson",
    description: "Turns off switch after 1 second",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


/**
 *  Turn me off quick
 *
 *  Author: seateabee@gmail.com
 *  Heavily based on Power Allowance by SmartThings
 *  Date: 2013-06-28
 */
preferences {
	section("When a switch turns on...") {
		input "theSwitch", "capability.switch"
	}
	section("Turn it off how many SECONDS later?") {
		input "secondsLater", "number", title: "When?"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def switchOnHandler(evt) {
	log.debug "Switch ${theSwitch} turned: ${evt.value}"
	def delay = secondsLater * 1000 /*Because delay is counted in mill-seconds, multiple seconds by 1000 to get the proper delay. */
	log.debug "Turning off in ${secondsLater} minutes (${delay}ms)"
	theSwitch.off(delay: delay)
}