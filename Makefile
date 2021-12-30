deploy: build
	@cp MANIFEST.mf tmp/MANIFEST.mf
	@cd tmp && jar cfmv ../fractal.jar MANIFEST.mf *.class fractals/*.class utils/*.class utils/complex/*.class

build:
	@mkdir tmp 2> /dev/null || true
	@cd src/ && javac Fractale.java -d ../tmp

clean:
	@rm -rf tmp