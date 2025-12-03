#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pdfplumber

pdf_path = r"c:\Mine\UNRI\Semester 3\PBO\ProjectPBOPraktikum\Aplikasi Keuangan Pribadi.pdf"

try:
    with pdfplumber.open(pdf_path) as pdf:
        print(f"Total Pages: {len(pdf.pages)}\n")
        print("="*80)
        
        for i, page in enumerate(pdf.pages):
            print(f"\n--- PAGE {i+1} ---\n")
            text = page.extract_text()
            print(text)
            print("\n" + "="*80)
        
except Exception as e:
    print(f"Error: {e}")
