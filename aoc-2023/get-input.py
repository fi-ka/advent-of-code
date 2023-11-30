import argparse
import requests
import os.path
import sys
import json

def get_input(day: int):
    url = f"https://adventofcode.com/2023/day/{day}/input"
    s = requests.session()
    session = get_session()
    s.cookies.set("session", session)
    try:
        response = requests.get(url, cookies=s.cookies)
        response.raise_for_status()
    except requests.exceptions.HTTPError as err:
        exit(err)
    return response.text

def create_input_file(day: int, input: str):
    dir_path=os.path.join(os.path.dirname(__file__), f"src/main/kotlin/aoc/day{day}")
    filepath=os.path.join(dir_path, "input.txt")
    print(filepath)
    os.makedirs(dir_path, exist_ok=True)
    with open(filepath, "w") as f:
        f.write(input)

def get_session():
    local_config=os.path.join(os.path.dirname(__file__), f"local_config.json")
    with open(local_config) as f:
        config = json.load(f)
        if "session" not in config:
            sys.exit("Error: aoc_session not specified in local_config.json")
        return config["session"]

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-d", "--day", type=int, required=True)
    args = parser.parse_args()
    input = get_input(args.day)
    create_input_file(args.day, input)
