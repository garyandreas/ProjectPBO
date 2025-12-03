#!/usr/bin/env python3
"""
Inspect serialized Java objects in .dat files
Quick & dirty inspection script
"""
import pickle
import sys
import os

def inspect_file(filename):
    if not os.path.exists(filename):
        print(f"❌ File not found: {filename}")
        return
    
    try:
        with open(filename, 'rb') as f:
            # Try to read as pickle (won't work for Java serialized, but worth trying)
            content = f.read()
            print(f"\n📄 File: {filename}")
            print(f"   Size: {len(content)} bytes")
            print(f"   First 100 bytes (hex): {content[:100].hex()}")
            print(f"   Readable part: {content[:100]}")
    except Exception as e:
        print(f"Error reading {filename}: {e}")

if __name__ == "__main__":
    files = ["users_data.dat", "accounts_data.dat", "categories_data.dat", "transactions_data.dat"]
    for f in files:
        inspect_file(f)
