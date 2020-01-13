/*
rleyvacortes22 is big brain 这是大脑时间
rmehta22
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.*;

@TeleOp(name= "Teleop", group = "Linear Opmode")
//@Disabled
public class Teleop extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    Servo gripper;
    CRServo  leftServo;
    CRServo rightServo;

    @Override
    public void runOpMode() {

        double speedAdjust = 7.0;
        double servoPos1 = 0.0;

        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gripper = hardwareMap.get(Servo.class, "gripper");

        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");

        gripper.setPosition(servoPos1);
        leftServo.setPower(0.0);
        rightServo.setPower(0.0);

        runtime.reset();



        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.dpad_left) {
                speedAdjust -= 1;
            }
            if(gamepad1.dpad_right) {
                speedAdjust += 1;
            }



            //Gripper Position
            if (gamepad2.left_bumper) {
                servoPos1 += 0.05;

                if (servoPos1 >= 0.5) {
                    servoPos1 = 0.9;
                }
            }
            else if (gamepad2.right_bumper) {
                servoPos1 -= 0.05;

                if (servoPos1 <= 0.5) {
                    servoPos1 = 0.0;
                }
            }
            gripper.setPosition(servoPos1);


            // Mecanum Driving Code

            leftBack.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*(-speedAdjust/10));

            rightBack.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*(-speedAdjust/10));

            leftFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*(-speedAdjust/10));

            rightFront.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*(-speedAdjust/10));


            // Pace this loop so jaw action is reasonable speed.
            sleep(20);
            telemetry.update();
        }
    }
}
