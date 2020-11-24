import os
import zipfile

import shutil

def copytree(src, dst, symlinks=False, ignore=None):
    for item in os.listdir(src):
        s = os.path.join(src, item)
        d = os.path.join(dst, item)
        if os.path.isdir(s):
            shutil.copytree(s, d, symlinks, ignore)
        else:
            shutil.copy2(s, d)

def mkdir(path):

	folder = os.path.exists(path)

	if not folder:                   #判断是否存在文件夹如果不存在则创建为文件夹
		os.makedirs(path)            #makedirs 创建文件时如果路径不存在会创建这个路径
		print("---  new folder...  ---")
		print("---  OK  ---")

	else:
		print( "---  There is this folder!  ---")

def unzip_file(zip_src, dst_dir):
    fz = zipfile.ZipFile(zip_src, 'r')
    for file in fz.namelist():
        fz.extract(file, dst_dir)

def decompiler_file(folder):
    dir_name = folder[2:]
    new_base_dir = folder+"_decompiler"
    mkdir(new_base_dir)
    copytree(dir_name, new_base_dir)

    allfile=[]
    for dirpath,dirnames,filenames in os.walk("./"+new_base_dir):
        for dir in dirnames:
            allfile.append(os.path.join(dirpath,dir))
        for name in filenames:
            allfile.append(os.path.join(dirpath, name))
    for each_file in allfile:
        if ".jar" in each_file:
            (filepath,tempfilename) = os.path.split(each_file)
            (filename,extension) = os.path.splitext(tempfilename)
            mkdir(os.path.dirname(each_file)+"/"+filename)
            os.system("java -cp java-decompiler.jar org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler -dgs=true "+each_file+" "+os.path.dirname(each_file)+"/"+filename)
            # print("java -cp java-decompiler.jar org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler -dgs=true "+each_file+" "+os.path.dirname(each_file)+"/"+filename)

            unzip_file(os.path.dirname(each_file)+"/"+filename+"/"+tempfilename,os.path.dirname(each_file)+"/"+filename+"/")
            # print(os.path.dirname(each_file)+"/"+filename+"/"+tempfilename,os.path.dirname(each_file)+"/"+filename+"/")
            os.remove(each_file)
            os.remove(os.path.dirname(each_file)+"/"+filename+"/"+tempfilename)



def decompiler_jar(jar):
    dir_name = jar[2:jar.find(".",1)]
    mkdir(dir_name)
    os.system("java -cp java-decompiler.jar org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler -dgs=true "+jar+" ./"+dir_name)
    unzip_file(os.getcwd()+"/"+dir_name+"/"+dir_name+".jar","./"+dir_name)



if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='args')
    parser.add_argument('--folder', '-f', help='the folder you want to decompiler')
    parser.add_argument('--jar', '-jar', help='the jar you want to decompiler')
    args = parser.parse_args()
    if os.path.exists(args.folder):
        # 批量反编译文件夹里面所有jar
        decompiler_file(args.folder)
    elif os.path.isfile(args.jar):
        # 反编译单独的jar文件
        decompiler_jar(args.jar)
    else:
        print("Usge: python decompiler.py -f ./file or python decompiler.py -f ./file -jar file.jar\njava-decompiler.jar py脚本 待处理文件必须放在同一文件夹")

    # decompiler_file("./webapp")

    # decompiler_jar("./test.jar")